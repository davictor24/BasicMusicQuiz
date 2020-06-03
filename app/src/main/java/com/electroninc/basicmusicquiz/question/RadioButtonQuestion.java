package com.electroninc.basicmusicquiz.question;

import android.graphics.drawable.Drawable;

import java.util.List;

public class RadioButtonQuestion extends Question {
    public List<String> options;

    public RadioButtonQuestion(String questionText, Drawable imageAsset, List<String> options, List<String> answers) {
        super(questionText, imageAsset, answers);
        this.options = options;
    }
}
