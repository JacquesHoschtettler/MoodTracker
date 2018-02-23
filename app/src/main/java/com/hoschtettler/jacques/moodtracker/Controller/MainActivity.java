package com.hoschtettler.jacques.moodtracker.Controller;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hoschtettler.jacques.moodtracker.Model.MoodList;
import com.hoschtettler.jacques.moodtracker.Model.Tools.Memorisation;
import com.hoschtettler.jacques.moodtracker.R;
import com.hoschtettler.jacques.moodtracker.Model.Mood;

import java.util.ArrayList;

/**
 * @author jacques
 * on 29/01/18
 * @version 1
 * Main part of the MoodTracker's controller
 * Initializes and displays the mood screen
 */
// This app must be usable with the Kitkat level (Android 4.4)
@TargetApi(19)              
public class MainActivity extends AppCompatActivity
{
    private ImageView mSmiley ;         // current icon of the mood.
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
    private Memorisation mMemo;             //  memorization object
    private Mood mCurrentMood ;             // current mood to display and to memorize.
    private MoodList mReferencedMoods ;     // List of the referenced moods


    /**
     * Initalization of the display and of the Mood
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main);

        // Plugging the elements of the main screen
        mSmiley = (ImageView) findViewById(R.id.Mood_icon) ;
        /* Version Java 8
        mMoodIcon = (ImageView) findViewById(R.id.Mood_icon) ;
        */
        mAdd_Comment = (ImageButton) findViewById(R.id.Add_comment) ;
        mHistory = (ImageButton) findViewById(R.id.Show_mood_week) ;
        mReferencedMoods = new MoodList() ;

        /** Initialization of the current mood
         * If the current day is tomorrow(or later) relative to the memorized day,
         * the moods are shifted of a day (the sixth day become the seventh, the
         * fifth become the sixth, etc.
         * Else the current mood is the mood memorized with the index 0
         */
        mMemo = new Memorisation() ;
        mCurrentMood =  mMemo.initializationOfTheMood() ;

        mSmiley.setImageResource(mReferencedMoods.getMoodName(mCurrentMood.getMoodIndex()));
        mSmiley.setBackgroundResource(mReferencedMoods.getMoodColor(mCurrentMood.getMoodIndex()));
    }

}
