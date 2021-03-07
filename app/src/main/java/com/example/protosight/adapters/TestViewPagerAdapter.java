package com.example.protosight.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.protosight.tabProjectFragment;
import com.example.protosight.tabTaskFragment;
import com.example.protosight.tabTestSetUpFragment;

public class TestViewPagerAdapter extends FragmentPagerAdapter {

    int numTabs;
    String testName;
    private String lastActivity;

    public TestViewPagerAdapter(@NonNull FragmentManager fm, int behavior, String testName, String lastActivity) {
        super(fm, behavior);
        this.numTabs = behavior;
        this.testName = testName;
        this.lastActivity = lastActivity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new tabTestSetUpFragment(testName, lastActivity);
            case 1:
                return new tabTaskFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numTabs;
    }
}
