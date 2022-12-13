package com.example.learnit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String selectedTopic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<LinearLayout> topics = Arrays.asList(findViewById(R.id.animals),
                findViewById(R.id.body),
                findViewById(R.id.family),
                findViewById(R.id.food));

        final Button startQuiz = findViewById(R.id.startQuizBtn);

        topics.get(0).setOnClickListener(view -> {
            selectedTopic = "Animal";
            changeBackOfSelectTopic(topics, 0);
        });

        topics.get(1).setOnClickListener(view -> {
            selectedTopic = "Body";
            changeBackOfSelectTopic(topics, 1);
        });
        topics.get(2).setOnClickListener(view -> {
            selectedTopic = "Family";
            changeBackOfSelectTopic(topics, 2);
        });
        topics.get(3).setOnClickListener(view -> {
            selectedTopic = "Food";
            changeBackOfSelectTopic(topics, 3);
        });


        startQuiz.setOnClickListener(view -> {
            if(selectedTopic.isEmpty()) {
                Toast.makeText(MainActivity.this,"Choose quiz", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra(Flags.SELECTED_TOPIC,selectedTopic);
                startActivity(intent);
                finish();
            }
        });
    }

    private void changeBackOfSelectTopic(List<LinearLayout> topics, int position) {
        for(LinearLayout topic : topics) {
            if(topic.equals(topics.get(position))) {
                topic.setBackgroundResource(R.drawable.round_back_white_stroke_30);
            } else {
                topic.setBackgroundResource(R.drawable.round_back_white_30);
            }
        }
    }
}