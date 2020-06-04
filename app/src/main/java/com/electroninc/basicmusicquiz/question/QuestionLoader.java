package com.electroninc.basicmusicquiz.question;

import android.content.Context;

import com.electroninc.basicmusicquiz.question.models.Question;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

public class QuestionLoader extends AsyncTaskLoader<List<Question>> {
    public QuestionLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Question> loadInBackground() {
        // Later, this could be implemented to load data from the Internet
        try {
            return QuestionUtils.compileQuestions(getContext());
        } catch (Exception e) {
            return null;
        }

    }
}
