package com.electroninc.basicmusicquiz.question.models;

import android.graphics.drawable.Drawable;

import java.util.Set;

public abstract class Question {
    private String questionText;
    private Drawable imageAsset;
    private Set<String> answers;

    public Question(String questionText, Drawable imageAsset, Set<String> answers) {
        this.questionText = questionText;
        this.imageAsset = imageAsset;
        this.answers = answers;
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
}
