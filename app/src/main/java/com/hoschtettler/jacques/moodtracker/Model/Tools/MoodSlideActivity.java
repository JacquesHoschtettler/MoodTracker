package com.hoschtettler.jacques.moodtracker.Model.Tools;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.hoschtettler.jacques.moodtracker.Model.MoodSlideFragment;
import com.hoschtettler.jacques.moodtracker.R;

/**
 * Created by jacques on 26/03/18.
 */

public class MoodSlideActivity extends FragmentActivity {
    /**
     * The number of pages of moods.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.Pager_View);
        mPagerAdapter = new MoodSlideAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class MoodSlideAdapter extends FragmentPagerAdapter {

        /**
         * Default constructor
         *
         * @param fragmentManager
         */
        public MoodSlideAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
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
}
