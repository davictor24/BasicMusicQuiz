package com.electroninc.basicmusicquiz.question;

import android.graphics.drawable.Drawable;

import java.util.List;

public abstract class Question {
    public String questionText;
    public Drawable imageAsset;
    public List<String> answers;

    public Question(String questionText, Drawable imageAsset, List<String> answers) {
        this.questionText = questionText;
        this.imageAsset = imageAsset;
        this.answers = answers;
    }
}
