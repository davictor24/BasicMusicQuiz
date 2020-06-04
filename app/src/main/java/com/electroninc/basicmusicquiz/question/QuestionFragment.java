package com.electroninc.basicmusicquiz.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        QuizViewModel viewModel = getViewModel();
        Question question = getQuestion();
        int questionType = QuestionUtils.getQuestionType(question);

        ((TextView) view.findViewById(R.id.question)).setText(question.getQuestionText());
        if (question.getImageAsset() != null) {
            ImageView imageView = view.findViewById(R.id.image_asset);
            imageView.setImageDrawable(question.getImageAsset());
            imageView.setVisibility(View.VISIBLE);
        }

        if (questionType == QuestionUtils.CHECK_BOX_QUESTION) {
            CheckBoxQuestion checkBoxQuestion = (CheckBoxQuestion) question;
            Map<String, String> checkBoxOptions = checkBoxQuestion.getOptions();
            List<String> keys = new ArrayList<>(checkBoxOptions.keySet());
            Collections.shuffle(keys);
            LinearLayout parentLayout = view.findViewById(R.id.options);

            for (String key : keys) {
                // TODO: Add IDs
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(checkBoxOptions.get(key));
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                parentLayout.addView(checkBox);
            }
        } else if (questionType == QuestionUtils.RADIO_BUTTON_QUESTION) {
            // TODO: Debug the radio buttons
            // TODO: Add IDs
            RadioButtonQuestion radioQuestion = (RadioButtonQuestion) question;
            Map<String, String> radioOptions = radioQuestion.getOptions();
            List<String> keys = new ArrayList<>(radioOptions.keySet());
            Collections.shuffle(keys);
            RadioGroup parentLayout = view.findViewById(R.id.options);

            for (String key : keys) {
                RadioButton radio = new RadioButton(getContext());
                radio.setText(radioOptions.get(key));
                parentLayout.addView(radio);
            }
        }

        Button button = view.findViewById(R.id.next_btn);
        final boolean finalQuestion = questionIndex >= viewModel.totalQuestions - 1;
        if (finalQuestion) button.setText(R.string.finish);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Calculate new score and update view model
                if (finalQuestion) {
                    Intent intent = new Intent(getActivity(), ScoreActivity.class);
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
}
