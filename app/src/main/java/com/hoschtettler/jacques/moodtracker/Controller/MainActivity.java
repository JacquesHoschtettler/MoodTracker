package com.hoschtettler.jacques.moodtracker.Controller;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hoschtettler.jacques.moodtracker.Model.MoodList;
import com.hoschtettler.jacques.moodtracker.Model.Tools.Memorisation;
import com.hoschtettler.jacques.moodtracker.R;
import com.hoschtettler.jacques.moodtracker.Model.Mood;

import java.util.ArrayList;

import static android.graphics.Color.DKGRAY;

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
    private ImageView mSmiley;         // current icon of the mood.
    private ImageButton mAdd_Comment;  // access to writing a comment.
    private ImageButton mHistory;      // access to moods of the seven last days.
    private Button mValidateComment;   // valide the writed commment
    private Button mEraseComment;      // erase the writed comment
    private RelativeLayout mAdd_Comment_Set; // windows where the comment is writing
    private EditText mComment;

    /**
     * Moods of the last seven days.
     * - index 0 is for the current day ;
     * - index 1 for the mood of yesterday ;
     * - index 2 for the mood of the day before yesterday ;
     * - ...
     */
    private ArrayList<Mood> mWeekMood = new ArrayList<>();
    private Memorisation mMemo;             //  memorization object
    private Mood mCurrentMood;             // current mood to display and to memorize.
    private MoodList mReferencedMoods;     // List of the referenced moods
    private View mV;
    private SharedPreferences mMoodsMemorized;

    // Identification of the history activity
    public static final int HISTORY_ACTIVITY_REQUEST_CODE = 7 ;
    public static final String HISTORY_MOODS_WEEKLY = "HISTORY_MOODS_WEEKLY" ;

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
        mSmiley          = (ImageView)      findViewById(R.id.Mood_icon);
        mAdd_Comment     = (ImageButton)    findViewById(R.id.Add_comment);
        mHistory         = (ImageButton)    findViewById(R.id.Show_mood_week);
        mValidateComment = (Button)         findViewById(R.id.Add_comment_validate_btn);
        mEraseComment    = (Button)         findViewById(R.id.Add_comment_erase_btn);
        mAdd_Comment_Set = (RelativeLayout) findViewById(R.id.Add_comment_set);
        mComment         = (EditText)       findViewById(R.id.Add_comment_view);

        // Set the interactives objects on listening position
        mAdd_Comment.setOnClickListener(this);
        mAdd_Comment_Set.setOnClickListener(this);
        mValidateComment.setOnClickListener(this);
        mEraseComment.setOnClickListener(this);
        mHistory.setOnClickListener(this);

        //Identifying the pressed button
        mAdd_Comment.setTag(0);
        mValidateComment.setTag(1);
        mEraseComment.setTag(2);
        mHistory.setTag(3);

        mReferencedMoods = new MoodList();

        mMoodsMemorized = getPreferences(MODE_PRIVATE);

        /** Initialization of the current mood
         * If the current day is tomorrow(or later) relative to the memorized day,
         * the moods are shifted of a day (the sixth day become the seventh, the
         * fifth become the sixth, etc.
         * Else the current mood is the mood memorized with the index 0
         */
        mMemo = new Memorisation();
        mCurrentMood = mMemo.initializationOfTheMood(mMoodsMemorized);

        int indexMood = mCurrentMood.getMoodIndex();
        mSmiley.setImageResource(mReferencedMoods.getMoodName(indexMood));
        mSmiley.setBackgroundResource(mReferencedMoods.getMoodColor(indexMood));

    }

    @Override
    public void onClick(View v)
    {
        int buttonIndex = (int) v.getTag();
        switch (buttonIndex)
        {
            case 0:
                mAdd_Comment_Set.setVisibility(View.VISIBLE);
                mComment.setEnabled(true);

                // Avoid a another try of writing a another comment or to go to history
                mAdd_Comment.setEnabled(false);
                mHistory.setEnabled(false) ;

                // Avoid to erase a previous comment
                mEraseComment.setEnabled(true);
                mEraseComment.setTextColor(getResources().getColor(R.color.colorPrimary));

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
                    mValidateComment.setEnabled(true);
                    mValidateComment.setTextColor(getResources().getColor(R.color.colorPrimary));
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
        mCurrentMood.setMoodComment(comment);
        mAdd_Comment_Set.setVisibility(View.INVISIBLE);
        mComment.setEnabled(false);
        mValidateComment.setEnabled(false);
        mValidateComment.setTextColor(DKGRAY) ;
        mEraseComment.setEnabled(false);
        mEraseComment.setTextColor(DKGRAY);

        mAdd_Comment.setEnabled(true);
        mHistory.setEnabled(true);
    }
}
