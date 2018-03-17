package com.hoschtettler.jacques.moodtracker.Controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hoschtettler.jacques.moodtracker.R;

public class WeekHistory extends AppCompatActivity implements View.OnClickListener
    {
    private View[] mDaysMood = new  View[7] ;
    private ImageButton[] mDaysComment = new ImageButton[7];
    private View[] mDaysComplement = new View[7];

    private SharedPreferences mMoodsMemorized;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_history);

        // Plugging the elements of the history display
        mDaysMood[0] = (View) findViewById(R.id.sevenDaysAgo_view) ;
        mDaysComment[0] = (ImageButton) findViewById(R.id.sevenDaysComment_btn) ;
        mDaysComplement[0]= (View) findViewById(R.id.sevenDaysAgoComplement_view) ;
        mDaysMood[1] = (View) findViewById(R.id.sixDaysAgo_view) ;
        mDaysComment[1] = (ImageButton) findViewById(R.id.sixDaysComment_btn) ;
        mDaysComplement[1]= (View) findViewById(R.id.sixDaysAgoComplement_view) ;
        mDaysMood[2] = (View) findViewById(R.id.fiveDaysAgo_view) ;
        mDaysComment[2] = (ImageButton) findViewById(R.id.fiveDaysComment_btn) ;
        mDaysComplement[2]= (View) findViewById(R.id.fiveDaysAgoComplement_view) ;
        mDaysMood[3] = (View) findViewById(R.id.fourDaysAgo_view) ;
        mDaysComment[3] = (ImageButton) findViewById(R.id.fourDaysComment_btn) ;
        mDaysComplement[3]= (View) findViewById(R.id.fourDaysAgoComplement_view) ;
        mDaysMood[4] = (View) findViewById(R.id.threeDaysAgo_view) ;
        mDaysComment[4] = (ImageButton) findViewById(R.id.threeDaysComment_btn) ;
        mDaysComplement[4]= (View) findViewById(R.id.threeDaysAgoComplement_view) ;
        mDaysMood[5] = (View) findViewById(R.id.twoDaysAgo_view) ;
        mDaysComment[5] = (ImageButton) findViewById(R.id.twoDaysComment_btn) ;
        mDaysComplement[5]= (View) findViewById(R.id.twoDaysAgoComplement_view) ;
        mDaysMood[6] = (View) findViewById(R.id.yesterday_view) ;
        mDaysComment[6] = (ImageButton) findViewById(R.id.yesterdayComment_btn) ;
        mDaysComplement[6]= (View) findViewById(R.id.yesterdayComplement_view) ;

        mMoodsMemorized = getPreferences(MODE_PRIVATE);

        // Initialisation of the moods



    }

        @Override
        public void onClick(View v) {

        }
    }
