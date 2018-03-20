package com.hoschtettler.jacques.moodtracker.Controller;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoschtettler.jacques.moodtracker.Model.Mood;
import com.hoschtettler.jacques.moodtracker.Model.MoodList;
import com.hoschtettler.jacques.moodtracker.Model.Tools.Memorisation;
import com.hoschtettler.jacques.moodtracker.R;

import java.util.ArrayList;

public class WeekHistory extends AppCompatActivity implements View.OnClickListener
    {
    private View[] mDaysMood = new  View[7] ;
    private ImageButton[] mDaysComment = new ImageButton[7];
    private ArrayList<Mood> mWeeklyMoods ;

    private MoodList mReferencedMoods;     // List of the referenced moods

    private SharedPreferences mMoodsMemorized;
    public static final String NAME_FILE_MEMORISATION = "MoodTracker_Memory" ;
    private static String PREFERENCES_KEY_MOODS = "PREFERENCES_KEY_MOODS" ;
    private static String PREFERENCES_KEY_COMMENT = "PREFERENCES_KEY_COMMENT" ;


        @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_history);

        // Plugging the elements of the history display
        mDaysMood[6] = (View) findViewById(R.id.sevenDaysAgo_view) ;
        mDaysComment[6] = (ImageButton) findViewById(R.id.sevenDaysComment_btn) ;
        mDaysMood[5] = (View) findViewById(R.id.sixDaysAgo_view) ;
        mDaysComment[5] = (ImageButton) findViewById(R.id.sixDaysComment_btn) ;
        mDaysMood[4] = (View) findViewById(R.id.fiveDaysAgo_view) ;
        mDaysComment[4] = (ImageButton) findViewById(R.id.fiveDaysComment_btn) ;
        mDaysMood[3] = (View) findViewById(R.id.fourDaysAgo_view) ;
        mDaysComment[3] = (ImageButton) findViewById(R.id.fourDaysComment_btn) ;
        mDaysMood[2] = (View) findViewById(R.id.threeDaysAgo_view) ;
        mDaysComment[2] = (ImageButton) findViewById(R.id.threeDaysComment_btn) ;
        mDaysMood[1] = (View) findViewById(R.id.twoDaysAgo_view) ;
        mDaysComment[1] = (ImageButton) findViewById(R.id.twoDaysComment_btn) ;
        mDaysMood[0] = (View) findViewById(R.id.yesterday_view) ;
        mDaysComment[0] = (ImageButton) findViewById(R.id.yesterdayComment_btn) ;

        // Identifying the buttons
        for (int i = 0 ; i <7 ; ++i)
        {
            mDaysComment[i].getTag(i) ;
        }

        mMoodsMemorized = getSharedPreferences(NAME_FILE_MEMORISATION, MODE_PRIVATE);
        Memorisation moodsMemory = new Memorisation(mMoodsMemorized) ;
        for (int i = 0 ; i < 7 ; ++i)
        {
            mWeeklyMoods.add(moodsMemory.getMemorizedMoods().get(i));
        }

        mReferencedMoods = new MoodList() ;

        // Initialisation of the moods
        for (int i = 0 ; i <7 ; ++i)
        {
          mDaysMood[i].setBackgroundColor(mWeeklyMoods.get(i).getMoodIndex());
            if (mWeeklyMoods.get(i).getMoodComment().equals((String)""))
            {
                mDaysComment[i].setVisibility(View.VISIBLE) ;
                mDaysComment[i].setEnabled(true);
            }
            else
            {
                mDaysComment[i].setVisibility(View.INVISIBLE);
                mDaysComment[i].setEnabled(false);
            }
        }
    }

        @Override
        public void onClick(View v)
        {
            //Test
            System.out.println("Bouton appelant : " + v.getTag()) ;


            String comment = mWeeklyMoods.get((int)v.getTag()).getMoodComment() ;
            Toast.makeText(this, comment, Toast.LENGTH_LONG).show() ;
        }
    }
