package com.satyajit.Speak_Japanese;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    /**
     * Variable to count number of tabs
     */
    int tabCount;
    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new Numbers();
            case 1: return new family();
            case 2: return new colors();
            case 3: return new phrases();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
