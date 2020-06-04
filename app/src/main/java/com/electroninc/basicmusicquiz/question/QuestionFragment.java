package com.electroninc.basicmusicquiz.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.electroninc.basicmusicquiz.QuizViewModel;
import com.electroninc.basicmusicquiz.R;
import com.electroninc.basicmusicquiz.question.models.Question;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class QuestionFragment extends Fragment {

    private static final String QUESTION_INDEX = "question_index";

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(int questionIndex) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(QUESTION_INDEX, questionIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int layoutResource;
        switch (QuestionUtils.getQuestionType(getQuestion())) {
            case QuestionUtils.CHECK_BOX_QUESTION:
                layoutResource = R.layout.question_checkbox;
                break;
            case QuestionUtils.RADIO_BUTTON_QUESTION:
                layoutResource = R.layout.question_radio;
                break;
            default:
                layoutResource = R.layout.question_text;
                break;
        }
        return inflater.inflate(layoutResource, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final int questionIndex = getQuestionIndex();
        final QuizViewModel viewModel = getViewModel();
        Question question = getQuestion();

        // TODO: Populate view and set event listeners

        Button button = view.findViewById(R.id.next_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionIndex >= viewModel.totalQuestions - 1)
                    Toast.makeText(getContext(), "You have reached the end of the quiz!", Toast.LENGTH_LONG).show();
                else ((ViewPager) getActivity().findViewById(R.id.questions_view_pager)).setCurrentItem(questionIndex + 1);
            }
        });
    }

    private int getQuestionIndex() {
        return getArguments().getInt(QUESTION_INDEX);
    }

    private QuizViewModel getViewModel() {
        return ViewModelProviders.of(getActivity()).get(QuizViewModel.class);
    }

    private Question getQuestion() {
        int questionIndex = getQuestionIndex();
        return getViewModel().questions.get(questionIndex);
    }
}
