package com.electroninc.basicmusicquiz.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.electroninc.basicmusicquiz.NonSwipeableViewPager;
import com.electroninc.basicmusicquiz.QuizViewModel;
import com.electroninc.basicmusicquiz.R;
import com.electroninc.basicmusicquiz.question.QuestionFragment;
import com.electroninc.basicmusicquiz.question.QuestionsAdapter;
import com.electroninc.basicmusicquiz.question.models.Question;
import com.electroninc.basicmusicquiz.question.QuestionLoader;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Question>> {

    private static final int LOADER_ID = 1;
    private QuizViewModel viewModel;
    private LinearLayout quizLoading;
    private NonSwipeableViewPager questionsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizLoading = findViewById(R.id.quiz_loading);
        questionsViewPager = findViewById(R.id.questions_view_pager);

        viewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        if (!viewModel.questionsSet)
            LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Question>> onCreateLoader(int id, @Nullable Bundle args) {
        return new QuestionLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Question>> loader, List<Question> data) {
        quizLoading.setVisibility(View.GONE);
        questionsViewPager.setVisibility(View.VISIBLE);
        viewModel.questionsSet = true;
        viewModel.questions = data;
        viewModel.totalQuestions = data.size();

        List<QuestionFragment> questionFragments = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
            questionFragments.add(QuestionFragment.newInstance(i));

        QuestionsAdapter questionsAdapter = new QuestionsAdapter(getSupportFragmentManager(), questionFragments);
        questionsViewPager.setAdapter(questionsAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Question>> loader) {
    }
}
