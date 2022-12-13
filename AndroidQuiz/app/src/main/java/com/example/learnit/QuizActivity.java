package com.example.learnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnit.topics.Topic;
import com.example.learnit.topics.TopicsResourceParsers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuizActivity extends AppCompatActivity {
    private static final long START_TIMER_IN_MILLIS = 10000;
    private long timeLeftInMills = START_TIMER_IN_MILLIS;
    private CountDownTimer countDownTimer;

    private TextView questionNumber;
    private TextView question;

    private String selectedOptionByUser = "";

    List<AppCompatButton> options;//варианты ответов
    private AppCompatButton nextBtn;


    private List<Topic> topics;//список распарсенных вопросов выбранной темы
    private int currentQuestionPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView timer = findViewById(R.id.timer);
        final TextView selectedTopicName = findViewById(R.id.selectedTopicName);

        questionNumber = findViewById(R.id.questionNumber);
        question = findViewById(R.id.question);

        options = Arrays.asList(findViewById(R.id.option1),
                findViewById(R.id.option2),
                findViewById(R.id.option3),
                findViewById(R.id.option4));


        nextBtn = findViewById(R.id.nextBtn);

        //получаем значение выбранной темы
        final String selectedTopic = getIntent().getStringExtra(Flags.SELECTED_TOPIC);

        selectedTopicName.setText(selectedTopic);

        //получаем список вопросов,возможных ответов и правильного ответа из распарсенного файла xml
        topics = TopicsResourceParsers.parse(getResources().getXml(R.xml.topics), selectedTopic);

        startTimer(timer);

        //устанавливаем начальные вопросы
        setTextViewsCurrentPosition();

        backBtn.setOnClickListener(view -> {
            countDownTimer.cancel();

            startActivity(new Intent(QuizActivity.this, MainActivity.class));
            finish();
        });

        for(AppCompatButton option : options) {
            option.setOnClickListener(view -> changeBackPressedBtn(option));
        }

        nextBtn.setOnClickListener(view -> {
            if(selectedOptionByUser.isEmpty()) {
                Toast.makeText(QuizActivity.this, "Choose an answer option", Toast.LENGTH_SHORT).show();
            } else {
                changeNextQuestion();
            }
        });
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        startActivity(new Intent(QuizActivity.this, MainActivity.class));
        finish();
    }

    private void setTextViewsCurrentPosition() {
        questionNumber.setText((currentQuestionPosition + 1)+"/"+ topics.size());
        question.setText(topics.get(currentQuestionPosition).getQuestion());

        options.get(0).setText(topics.get(currentQuestionPosition).getOption1());
        options.get(1).setText(topics.get(currentQuestionPosition).getOption2());
        options.get(2).setText(topics.get(currentQuestionPosition).getOption3());
        options.get(3).setText(topics.get(currentQuestionPosition).getOption4());
    }

    /*
     * Изменение внешего вида выбранной кнопки
     * Если вариант ответа верен, то кнопка окрашивается с помощью метода revealAnswer() в зеленый
     * В противном случае в красный и подсвечивается верный вариант
     * */
    private void changeBackPressedBtn(AppCompatButton selectedOption) {
        if(selectedOptionByUser.isEmpty()) {
            selectedOptionByUser = selectedOption.getText().toString();
            selectedOption.setBackgroundResource(R.drawable.round_back_red_30);
            selectedOption.setTextColor(Color.WHITE);

            revealAnswer();
            topics.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
        }
    }

    private void revealAnswer() {
        final String getAnswer = topics.get(currentQuestionPosition).getAnswer();

        for(AppCompatButton option : options) {
            if(option.getText().toString().equals(getAnswer)) {
                option.setBackgroundResource(R.drawable.round_back_green_30);
                option.setTextColor(Color.WHITE);
            }
        }
    }

    private void changeNextQuestion() {
        currentQuestionPosition++;
        if((currentQuestionPosition + 1) == topics.size()) {
            nextBtn.setText(R.string.ready);
        }
        if(currentQuestionPosition < topics.size()) {
            selectedOptionByUser = ""; //сбрасываем выбранный ответ
            for(AppCompatButton option : options) {
                option.setBackgroundResource(R.drawable.round_back_white_30);
                option.setTextColor(getResources().getColor(R.color.big_stone));
            }
            setTextViewsCurrentPosition();
        } else {
            showQuizResult();
        }
    }

    private void showQuizResult() {
        countDownTimer.cancel();
        Intent intent = new Intent(QuizActivity.this, QuizResults.class);
        intent.putExtra(Flags.CORRECT, getScores().get(Flags.CORRECT));
        intent.putExtra(Flags.INCORRECT, getScores().get(Flags.INCORRECT));
        startActivity(intent);

        finish();
    }

    private Map<String,Integer> getScores() {
        Map<String,Integer> scores = new HashMap<>();
        int correctAnswer = 0;
        int incorrectAnswer = 0;

        scores.put(Flags.CORRECT,correctAnswer);
        scores.put(Flags.INCORRECT,incorrectAnswer);

        for(Topic topic : topics) {
            String userSelectedAnswer = topic.getUserSelectedAnswer();
            String answer = topic.getAnswer();

            if(userSelectedAnswer != null && userSelectedAnswer.equals(answer)) {
                scores.put(Flags.CORRECT, ++correctAnswer);
            } else {
                scores.put(Flags.INCORRECT, ++incorrectAnswer);
            }
        }
        return scores;
    }

    /*
    * Не смогла вынести startTimer() в отдельный класс в силу того,
    * что он вызывает функцию showQuizResult(),
    * которая логически располагается в QuizActivity
    * */
    private void startTimer(TextView timerTextView) {
        countDownTimer = new CountDownTimer(timeLeftInMills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMills = millisUntilFinished;

                int minutes = (int) (timeLeftInMills / 1000) / 60;
                int seconds = (int) (timeLeftInMills / 1000) % 60;

                String timeLeftFormatted = String.format("%02d:%02d",minutes,seconds);
                timerTextView.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizActivity.this, "Time is up!", Toast.LENGTH_SHORT).show();
                showQuizResult();
            }
        }.start();
    }
}