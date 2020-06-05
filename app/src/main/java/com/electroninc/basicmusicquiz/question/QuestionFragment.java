package com.electroninc.basicmusicquiz.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.electroninc.basicmusicquiz.QuizViewModel;
import com.electroninc.basicmusicquiz.R;
import com.electroninc.basicmusicquiz.activities.ScoreActivity;
import com.electroninc.basicmusicquiz.question.models.CheckBoxQuestion;
import com.electroninc.basicmusicquiz.question.models.Question;
import com.electroninc.basicmusicquiz.question.models.RadioButtonQuestion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        final Question question = getQuestion();
        final int questionType = QuestionUtils.getQuestionType(question);

        ((TextView) view.findViewById(R.id.question)).setText(question.getQuestionText());
        if (question.getImageAsset() != null) {
            ImageView imageView = view.findViewById(R.id.image_asset);
            imageView.setImageDrawable(question.getImageAsset());
            imageView.setVisibility(View.VISIBLE);
        }

        if (questionType == QuestionUtils.CHECK_BOX_QUESTION) {
            CheckBoxQuestion checkBoxQuestion = (CheckBoxQuestion) question;
            Map<String, String> checkBoxOptions = checkBoxQuestion.getOptions();
            List<String> keys = checkBoxQuestion.getOptionsOrder();
            LinearLayout parentLayout = view.findViewById(R.id.options);

            if (getCheckBoxState() == null) {
                Map<String, Boolean> checkBoxState = new HashMap<>();
                for (String key : keys) checkBoxState.put(key, false);
                setCheckBoxState(checkBoxState);
            }

            for (final String key : keys) {
                final CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(checkBoxOptions.get(key));
                checkBox.setChecked(getCheckBoxState().get(key));
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean newState = !getCheckBoxState().get(key);
                        getCheckBoxState().put(key, newState);
                        checkBox.setChecked(newState);
                    }
                });
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                parentLayout.addView(checkBox);
            }

        } else if (questionType == QuestionUtils.RADIO_BUTTON_QUESTION) {
            RadioButtonQuestion radioQuestion = (RadioButtonQuestion) question;
            Map<String, String> radioOptions = radioQuestion.getOptions();
            List<String> keys = radioQuestion.getOptionsOrder();
            RadioGroup parentLayout = view.findViewById(R.id.options);

            for (final String key : keys) {
                RadioButton radio = new RadioButton(getContext());
                radio.setText(radioOptions.get(key));
                // TODO: Are radio button states saved by default?
                // radio.setChecked(key.equals(getRadioButtonState()));
                radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRadioButtonState(key);
                    }
                });
                parentLayout.addView(radio);
            }
        }

        final Button button = view.findViewById(R.id.next_btn);
        final boolean finalQuestion = questionIndex >= viewModel.totalQuestions - 1;
        if (finalQuestion) button.setText(R.string.finish);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> answers = question.getAnswers();
                Set<String> givenAnswers = new HashSet<>();
                if (questionType == QuestionUtils.CHECK_BOX_QUESTION) {
                    for (String key : getCheckBoxState().keySet())
                        if (getCheckBoxState().get(key)) givenAnswers.add(key);
                } else if (questionType == QuestionUtils.RADIO_BUTTON_QUESTION) {
                    givenAnswers.add(getRadioButtonState());
                } else {
                    EditText textAnswer = getActivity().findViewById(R.id.text_answer);
                    givenAnswers.add(textAnswer.getText().toString());
                }
                if (QuestionUtils.checkAnswer(answers, givenAnswers)) viewModel.score += 1;

                if (finalQuestion) {
                    Intent intent = new Intent(getActivity(), ScoreActivity.class);
                    intent.putExtra(ScoreActivity.QUIZ_SCORE, viewModel.score);
                    intent.putExtra(ScoreActivity.QUIZ_MAX_SCORE, viewModel.totalQuestions);
                    startActivity(intent);
                    getActivity().finish();
                } else
                    ((ViewPager) getActivity().findViewById(R.id.questions_view_pager)).setCurrentItem(questionIndex + 1);
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

    private Map<String, Boolean> getCheckBoxState() {
        return getViewModel().checkBoxStates.get(getQuestionIndex());
    }

    private void setCheckBoxState(Map<String, Boolean> checkBoxState) {
        getViewModel().checkBoxStates.put(getQuestionIndex(), checkBoxState);
    }

    private String getRadioButtonState() {
        return getViewModel().radioButtonStates.get(getQuestionIndex());
    }

    private void setRadioButtonState(String radioButtonState) {
        getViewModel().radioButtonStates.put(getQuestionIndex(), radioButtonState);
    }
}
