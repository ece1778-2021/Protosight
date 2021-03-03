package com.example.protosight.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.protosight.tabProjectFragment;
import com.example.protosight.tabTaskFragment;

public class TestViewPagerAdapter extends FragmentPagerAdapter {

    int numTabs;
    public TestViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new tabProjectFragment();
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
