package com.electroninc.basicmusicquiz.question.models;

import android.graphics.drawable.Drawable;

import com.electroninc.basicmusicquiz.question.QuestionUtils;

import java.util.HashSet;
import java.util.Set;

public abstract class Question {
    private String questionText;
    private Drawable imageAsset;
    private Set<String> answers;
    private Set<String> playerAnswers;

    public Question(String questionText, Drawable imageAsset, Set<String> answers) {
        this.questionText = questionText;
        this.imageAsset = imageAsset;
        this.answers = answers;
        this.playerAnswers = new HashSet<>();
    }

    public String getQuestionText() {
        return questionText;
    }

    public Drawable getImageAsset() {
        return imageAsset;
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public Set<String> getPlayerAnswers() {
        return playerAnswers;
    }

    public void setPlayerAnswers(Set<String> playerAnswers) {
        this.playerAnswers = playerAnswers;
    }

    public boolean isCorrectAnswer() {
        return QuestionUtils.diff(answers, playerAnswers).isEmpty();
    }
}
