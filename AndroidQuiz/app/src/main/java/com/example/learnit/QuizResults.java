package com.example.learnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuizResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        final AppCompatButton goBackMenu = findViewById(R.id.goBackMenu);
        final TextView correctAnswer = findViewById(R.id.correctAnswer);
        final TextView incorrectAnswer = findViewById(R.id.incorrectAnswer);

        final int correctAnswers = getIntent().getIntExtra(Flags.CORRECT,0);
        final int incorrectAnswers = getIntent().getIntExtra(Flags.INCORRECT,0);

        correctAnswer.setText("Correct answers: " + correctAnswers);
        incorrectAnswer.setText("Incorrect answers: " + incorrectAnswers);

        goBackMenu.setOnClickListener(view -> {
            startActivity(new Intent(QuizResults.this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizResults.this, MainActivity.class));
        finish();
    }
}