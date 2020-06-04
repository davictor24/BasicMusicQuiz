package com.electroninc.basicmusicquiz.question.models;

import android.graphics.drawable.Drawable;

import java.util.Set;

public class TextEntryQuestion extends Question {
    public TextEntryQuestion(String questionText, Drawable imageAsset, Set<String> answers) {
        super(questionText, imageAsset, answers);
    }
}
