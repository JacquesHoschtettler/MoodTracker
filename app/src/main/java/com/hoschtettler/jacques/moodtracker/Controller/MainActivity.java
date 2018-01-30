package com.hoschtettler.jacques.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hoschtettler.jacques.moodtracker.R;
import com.hoschtettler.jacques.moodtracker.model.Mood;

/**
 * @author jacques
 * on 29/01/18
 * Main part of the MoodTracker's controller
 * Initializes and displays the mood screen
 */

public class MainActivity extends AppCompatActivity {

    private View mMoodBackground ;      // current color of the mood
    private ImageView mMoodIcom ;       // current icon of the mood
    private Mood mCurrentMood;          // current mood to display and to memorize

    private ImageButton mAdd_Comment ;  // access to writing a comment

    /**
     * Initalization of the display and of the Mood
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mCurrentMood = new Mood();
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main);
    }
}
