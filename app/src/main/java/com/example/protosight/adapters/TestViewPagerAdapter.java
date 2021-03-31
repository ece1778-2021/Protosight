package com.example.protosight.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.protosight.TabTestSetUpFragment;
import com.example.protosight.TabTestResultsFragment;

public class TestViewPagerAdapter extends FragmentPagerAdapter {

    int numTabs;
    private String testID;
    private String lastActivity;
    private String projectCode;

    public TestViewPagerAdapter(@NonNull FragmentManager fm,
                                int behavior,
                                String testID,
                                String lastActivity,
                                String projectCode) {
        super(fm, behavior);
        this.numTabs = behavior;
        this.testID = testID;
        this.lastActivity = lastActivity;
        this.projectCode = projectCode;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabTestSetUpFragment(testID, lastActivity, projectCode);
            case 1:
                return new TabTestResultsFragment(testID);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numTabs;
    }
}
