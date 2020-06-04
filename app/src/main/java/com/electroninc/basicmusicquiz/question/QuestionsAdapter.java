package com.electroninc.basicmusicquiz.question;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class QuestionsAdapter extends FragmentPagerAdapter {
    private List<QuestionFragment> questionFragments;

    public QuestionsAdapter(FragmentManager fm, List<QuestionFragment> questionFragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.questionFragments = questionFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return questionFragments.get(position);
    }

    @Override
    public int getCount() {
        return questionFragments.size();
    }
}
