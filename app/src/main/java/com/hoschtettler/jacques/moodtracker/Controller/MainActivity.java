package com.hoschtettler.jacques.moodtracker.Controller;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hoschtettler.jacques.moodtracker.Model.Mood;
import com.hoschtettler.jacques.moodtracker.Model.MoodList;
import com.hoschtettler.jacques.moodtracker.Model.MoodSlideFragment;
import com.hoschtettler.jacques.moodtracker.Model.Tools.Memorisation;
import com.hoschtettler.jacques.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author jacques
 * on 29/01/18
 * @version 1
 * Main part of the MoodTracker's controller
 * Initializes and displays the mood screen
 */
// This app must be usable with the Kitkat level (Android 4.4)
@TargetApi(19)
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String BUNDLE_STATE_MOOD_INDEX = "currentMoodIndex";

    private ImageButton mAdd_Comment;  // access to writing a comment.
    private ImageButton mHistory;      // access to moods of the seven last days.
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

    private EditText mComment;          // windows where the comment is writing
    private Button mValidateComment;   // valide the writed commment
    private Button mEraseComment;      // erase the writed comment
    private View mComment_Complement; // empty space to fill below the EditText

    /**
     * Moods of the last seven days.
     * - index 0 is for the current day ;
     * - index 1 for the mood of yesterday ;
     * - index 2 for the mood of the day before yesterday ;
     * - ...
     */
    private ArrayList<Mood> mWeekMood = new ArrayList<>(7);
    private Memorisation mMemo;             //  memorization object
    private Mood mCurrentMood;             // current mood to display and to memorize.
    private MoodList mReferencedMoods;     // List of the referenced moods
    private SharedPreferences mMoodsMemorized;

    // Identification of the history activity
    public static final int HISTORY_ACTIVITY_REQUEST_CODE = 7 ;
    public static final String HISTORY_MOODS_WEEKLY = "HISTORY_MOODS_WEEKLY" ;
    public static final String NAME_FILE_MEMORISATION = "MoodTracker_Memory" ;


    /**
     * Initalization of the display and of the Mood
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

        // Plugging the elements of the main screen
        mAdd_Comment     = (ImageButton)    findViewById(R.id.Add_comment);
        mHistory         = (ImageButton)    findViewById(R.id.Show_mood_week);

        mValidateComment = (Button)         findViewById(R.id.Add_comment_validate_btn);
        mEraseComment    = (Button)         findViewById(R.id.Add_comment_erase_btn);
        mComment         = (EditText)       findViewById(R.id.Add_comment_view);
        mComment_Complement = (View)        findViewById(R.id.Add_comment_complement);

        // Set the interactives objects on listening position
        mAdd_Comment.setOnClickListener(this);
        mHistory.setOnClickListener(this);
        mValidateComment.setOnClickListener(this);
        mEraseComment.setOnClickListener(this);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.Pager_View);
        mPagerAdapter = new MoodSlideAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //Identifying the pressed button
        mAdd_Comment.setTag(0);
        mValidateComment.setTag(1);
        mEraseComment.setTag(2);
        mHistory.setTag(3);

        mReferencedMoods = new MoodList();

        mMoodsMemorized = getSharedPreferences(NAME_FILE_MEMORISATION, MODE_PRIVATE);

        /** Initialization of the current mood
         * If the current day is tomorrow(or later) relative to the memorized day,
         * the moods are shifted of a day (the sixth day become the seventh, the
         * fifth become the sixth, etc.
         * Else the current mood is the mood memorized with the index 0
         */
        mMemo = new Memorisation(mMoodsMemorized);
        mCurrentMood = mMemo.initializationOfTheMood(mMoodsMemorized);

        int indexMood = mCurrentMood.getMoodIndex();
        mPager.setCurrentItem(indexMood);
    }

    @Override
    public void onClick(View v)
    {
        int buttonIndex = (int) v.getTag();
        switch (buttonIndex)
        {
            case 0:
                mComment.setVisibility(View.VISIBLE);
                mComment_Complement.setVisibility(View.VISIBLE);
                mValidateComment.setVisibility(View.VISIBLE);
                mEraseComment.setVisibility(View.VISIBLE);

                // Avoid a another try of writing a another comment or to go to history
                mAdd_Comment.setEnabled(false);
                mHistory.setEnabled(false) ;

                // Avoid to erase or to validate a previous comment
                mComment.setEnabled(true);
                mEraseComment.setEnabled(true);
                mValidateComment.setEnabled(true);

                String tempString = mCurrentMood.getMoodComment();
                mComment.setText(tempString);

                mComment.addTextChangedListener(new TextWatcher()
                {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after)
                  {
                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count)
                  {
                  }

                  @Override
                  public void afterTextChanged(Editable s)
                  {
                  }
                }
                );
            break;

            case 1 :
                tempString = mComment.getText().toString();
                closeAddComment(tempString);
            break;

            case 2 :
                tempString = "" ;
                closeAddComment(tempString);
            break;
            case 3 :
                Intent historyActivity = new Intent(MainActivity.this, WeekHistory.class) ;
                startActivity(historyActivity);
            break ;
        }
    }

    private void closeAddComment(String comment)
    {
        mCurrentMood.setMoodComment(comment);
        mMemo.setMemorisationCurrentComment(mMoodsMemorized, comment);
        mComment.setVisibility(View.INVISIBLE);
        mComment.setEnabled(false);
        mComment_Complement.setVisibility(View.INVISIBLE);
        mValidateComment.setVisibility(View.INVISIBLE);
        mValidateComment.setEnabled(false);
        mEraseComment.setVisibility(View.INVISIBLE);
        mEraseComment.setEnabled(false);

        mAdd_Comment.setEnabled(true);
        mHistory.setEnabled(true);
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


    protected void onSaveInstanceState(Bundle outState) {
        outState.getInt(BUNDLE_STATE_MOOD_INDEX, mPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

}
