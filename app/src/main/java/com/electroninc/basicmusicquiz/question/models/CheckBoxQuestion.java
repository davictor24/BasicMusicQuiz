package com.electroninc.basicmusicquiz.question.models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckBoxQuestion extends Question {
    private Map<String, String> options;
    private List<String> optionsOrder;

    public CheckBoxQuestion(String questionText, Drawable imageAsset, Map<String, String> options, Set<String> answers) {
        super(questionText, imageAsset, answers);
        this.options = options;
        this.optionsOrder = new ArrayList<>(options.keySet());
        Collections.shuffle(optionsOrder);
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public List<String> getOptionsOrder() {
        return optionsOrder;
    }
}
