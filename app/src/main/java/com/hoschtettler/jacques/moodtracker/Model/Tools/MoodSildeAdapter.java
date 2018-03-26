package com.hoschtettler.jacques.moodtracker.Model.Tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hoschtettler.jacques.moodtracker.Model.MoodSlideFragment;

/**
 * Created by jacques on 26/03/18.
 */

public class MoodSildeAdapter extends FragmentPagerAdapter {
    /**
     * The number of pages of moods.
     */
    private static final int NUM_PAGES = 5;


    /**
     * Default constructor
     *
     * @param fm
     */
    public MoodSildeAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Creating the new view
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {

        return MoodSlideFragment.create(position);
    }

    /**
     * Return the number of moods
     *
     * @return NUM_PAGES
     */
    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
