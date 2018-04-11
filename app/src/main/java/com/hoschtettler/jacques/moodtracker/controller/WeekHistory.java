package com.hoschtettler.jacques.moodtracker.controller;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hoschtettler.jacques.moodtracker.R;
import com.hoschtettler.jacques.moodtracker.model.Mood;
import com.hoschtettler.jacques.moodtracker.model.MoodList;
import com.hoschtettler.jacques.moodtracker.model.Tools.Memorisation;

import java.util.ArrayList;

import static com.hoschtettler.jacques.moodtracker.controller.MainActivity.NAME_FILE_MEMORISATION;
import static com.hoschtettler.jacques.moodtracker.model.MoodList.NUMBER_MOOD;
import static com.hoschtettler.jacques.moodtracker.model.Tools.Memorisation.PREFERENCES_KEY_STAT;

@TargetApi(19)
public class WeekHistory extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<TextView> mDaysMood = new ArrayList<>(7) ;
    private ImageButton[] mDaysComment = new ImageButton[7];
    private ArrayList<Mood> mWeeklyMoods = new ArrayList<>(7) ;
    private Button mStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_history);

        // Plugging the elements of the history display
        mDaysMood.add((TextView) findViewById(R.id.sevenDaysAgo_view)) ;
        mDaysComment[0] = findViewById(R.id.sevenDaysComment_btn);
        mDaysMood.add((TextView) findViewById(R.id.sixDaysAgo_view)) ;
        mDaysComment[1] = findViewById(R.id.sixDaysComment_btn);
        mDaysMood.add((TextView) findViewById(R.id.fiveDaysAgo_view)) ;
        mDaysComment[2] = findViewById(R.id.fiveDaysComment_btn);
        mDaysMood.add((TextView) findViewById(R.id.fourDaysAgo_view)) ;
        mDaysComment[3] = findViewById(R.id.fourDaysComment_btn);
        mDaysMood.add((TextView) findViewById(R.id.threeDaysAgo_view)) ;
        mDaysComment[4] = findViewById(R.id.threeDaysComment_btn);
        mDaysMood.add((TextView) findViewById(R.id.twoDaysAgo_view)) ;
        mDaysComment[5] = findViewById(R.id.twoDaysComment_btn);
        mDaysMood.add((TextView) findViewById(R.id.yesterday_view)) ;
        mDaysComment[6] = findViewById(R.id.yesterdayComment_btn);

        // Plugging the button to go to the statistics
        mStatistics = findViewById(R.id.statistics_btn);

        // Allows the buttons to be listenned
        for (int i = 0; i < 7; ++i) {
            mDaysComment[i].setOnClickListener(this);
        }
        mStatistics.setOnClickListener(this);

        // Identifying the buttons
        for (int i = 0; i < 7; ++i) {
            mDaysComment[i].setTag(i) ;
        }
        mStatistics.setTag(7);

        // Calculing the basic width for the displaying of the moods
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int weight0 = metrics.widthPixels / NUMBER_MOOD;

        /* Loading the memorized moods of the seven past days. They are memorized from
         0 (today) to 7 (a week ago). But they are used from 0 (a week ago) to
         6 (yesterday).
         */
        SharedPreferences moodsMemorized = getSharedPreferences(NAME_FILE_MEMORISATION,
                MODE_PRIVATE);
        Memorisation moodsMemory = new Memorisation(moodsMemorized);
        for (int i = 0; i < 7; ++i) {
            mWeeklyMoods.add(moodsMemory.getMemorizedMoods().get(6 - i));
        }

        // Creation of the list of references for the moods
        MoodList referencedMoods = new MoodList();


        // Initialisation of the moods
        for (int i = 0; i < 7; ++i) {
            int moodPassed = mWeeklyMoods.get(i).getMoodIndex() ;
            mDaysMood.get(i).setBackgroundResource(referencedMoods.
                    getMoodColor(moodPassed));
            mDaysMood.get(i).setWidth((moodPassed + 1) * weight0);
            mDaysMood.get(i).setEnabled(true);

            /* Avoid or not the displaying of the button to "toasting" the comment, if
            it exists.
            */
            if (mWeeklyMoods.get(i).getMoodComment().equals("")) {
                mDaysComment[i].setVisibility(View.INVISIBLE) ;
                mDaysComment[i].setEnabled(false);
            } else {
                mDaysComment[i].setVisibility(View.VISIBLE);
                mDaysComment[i].setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (((int) v.getTag() == 7)) {
            // Starting the Statistics activity
            Intent statisticActivity = new Intent(WeekHistory.this,
                    Statistics.class);
            startActivity(statisticActivity);
        } else {
            //Displaying the comment of the selected day.
            String comment = mWeeklyMoods.get((int) v.getTag()).getMoodComment();
            Toast.makeText(this, comment, Toast.LENGTH_LONG).show();
        }
    }
}
