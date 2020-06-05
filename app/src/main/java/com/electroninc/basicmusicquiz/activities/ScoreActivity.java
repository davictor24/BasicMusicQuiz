package com.electroninc.basicmusicquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.electroninc.basicmusicquiz.R;

public class ScoreActivity extends AppCompatActivity {

    public static final String QUIZ_SCORE = "quiz_score";
    public static final String QUIZ_MAX_SCORE = "quiz_max_score";

    private int score;
    private int maxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        score = intent.getIntExtra(QUIZ_SCORE, 0);
        maxScore = intent.getIntExtra(QUIZ_MAX_SCORE, 1);
        Toast.makeText(this, "You scored: " + score, Toast.LENGTH_LONG).show();
    }
}
