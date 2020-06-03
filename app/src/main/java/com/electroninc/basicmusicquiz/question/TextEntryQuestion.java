package com.electroninc.basicmusicquiz.question;

import android.graphics.drawable.Drawable;

import java.util.List;

public class TextEntryQuestion extends Question {
    public TextEntryQuestion(String questionText, Drawable imageAsset, List<String> answers) {
        super(questionText, imageAsset, answers);
    }
}
