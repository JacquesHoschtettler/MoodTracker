package com.hoschtettler.jacques.moodtracker.Controller;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hoschtettler.jacques.moodtracker.Model.Tools.Memorisation;
import com.hoschtettler.jacques.moodtracker.R;
import com.hoschtettler.jacques.moodtracker.Model.Mood;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author jacques
 * on 29/01/18
 * @version 1
 * Main part of the MoodTracker's controller
 * Initializes and displays the mood screen
 */

public class MainActivity extends AppCompatActivity {

    private View mMoodBackground ;      // current color of the mood.
    private ImageView mMoodIcon ;       // current icon of the mood.
    private Mood mCurrentMood;          // current mood to display and to memorize.

    private ImageButton mAdd_Comment ;  // access to writing a comment.
    private ImageButton mHistory ;      // access to moods of the seven last days.

    /**
     * Moods of the last seven days.
     *      - index 0 is for the current day ;
     *      - index 1 for the mood of yesterday ;
     *      - index 2 for the mood of the day before yesterday ;
     *      - ...
     */
    private ArrayList<Mood> mWeekMood = new ArrayList<>();
    private Memorisation mMemo;         //  memorization object


    /**
     * Initalization of the display and of the Mood
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Plugging the elements of the main screen
        mMoodBackground = (View) findViewById(R.id.Mood_background) ;
        mMoodIcon = (ImageView) findViewById(R.id.Mood_icon) ;
        mAdd_Comment = (ImageButton) findViewById(R.id.Add_comment) ;
        mHistory = (ImageButton) findViewById(R.id.Show_mood_week) ;

        /** Initialization of the current mood
         * If the current day is tomorrow(or later) relative to the memorized day,
         * the moods are shifted of a day (the sixth day become the seventh, the
         * fifth become the sixth, etc.
         * Else the current mood is the mood memorized with the index 0
         */
        mMemo = new Memorisation() ;
        Mood currentMood =  mMemo.initializationOfTheMood() ;
        mMoodIcon.setImageResource("@mipmap/" + currentMood.getMoodIndex());

        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main);
    }


}
