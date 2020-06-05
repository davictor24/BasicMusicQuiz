package com.electroninc.basicmusicquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.electroninc.basicmusicquiz.R;

public class ScoreActivity extends AppCompatActivity {

    public static final String QUIZ_SCORE = "quiz_score";
    public static final String QUIZ_MAX_SCORE = "quiz_max_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        int score = intent.getIntExtra(QUIZ_SCORE, 0);
        int maxScore = intent.getIntExtra(QUIZ_MAX_SCORE, 1);
        String formattedScore = score + "/" + maxScore;
        long percent = Math.round((float) score * 100.0 / maxScore);
        Toast.makeText(this, "You scored: " + formattedScore, Toast.LENGTH_LONG).show();

        TextView finalScoreTextView = findViewById(R.id.final_score);
        finalScoreTextView.setText(formattedScore);
        TextView recommendationTextView = findViewById(R.id.recommendation);
        recommendationTextView.setText(percent >= 70 ? getString(R.string.well_done) : getString(R.string.you_can_do_better));

        Button playAgainBtn = findViewById(R.id.play_again_btn);
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, QuizActivity.class);
                startActivity(intent);
                ScoreActivity.this.finish();
            }
        });
    }
}
