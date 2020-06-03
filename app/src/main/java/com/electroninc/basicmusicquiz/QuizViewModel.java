package com.electroninc.basicmusicquiz;

import com.electroninc.basicmusicquiz.question.Question;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {
    public boolean questionsSet = false;
    public List<Question> questions = new ArrayList<>();
}
