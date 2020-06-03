package com.electroninc.basicmusicquiz.question;

import android.graphics.drawable.Drawable;

import java.util.Map;
import java.util.Set;

public class RadioButtonQuestion extends Question {
    private Map<String, String> options;

    public RadioButtonQuestion(String questionText, Drawable imageAsset, Map<String, String> options, Set<String> answers) {
        super(questionText, imageAsset, answers);
        this.options = options;
    }

    public Map<String, String> getOptions() {
        return options;
    }
}
