package com.hoschtettler.jacques.moodtracker.controller;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hoschtettler.jacques.moodtracker.R;
import com.hoschtettler.jacques.moodtracker.model.Mood;
import com.hoschtettler.jacques.moodtracker.model.MoodList;
import com.hoschtettler.jacques.moodtracker.model.Tools.Memorisation;

import java.util.ArrayList;

import static android.support.v4.media.AudioAttributesCompat.USAGE_MEDIA;
import static com.hoschtettler.jacques.moodtracker.model.MoodList.NUMBER_MOOD;


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
        implements View.OnClickListener, SoundPool.OnLoadCompleteListener
{
    private static final String BUNDLE_STATE_MOOD_INDEX = "currentMoodIndex";
    private static final String BUNDLE_STATE_SOUND_ON = "soundEnabled";

    private ArrayList<ImageView> mMoodScreen;  // Views of the moods
    private ImageButton mAdd_Comment;  // access to writing a comment.
    private ImageButton mHistory;      // access to moods of the seven last days.
    private ImageButton mSoundOn;      // to put the sound on
    private ImageButton mSoundOff;     // to put the sound off

    private EditText mComment;          // windows where the comment is writing
    private Button mValidateComment;   // valide the writed commment
    private Button mEraseComment;      // erase the writed comment
    private View mComment_Complement; // empty space to fill below the EditText

    private Memorisation mMemo;             //  memorization object
    private Mood mCurrentMood;             // current mood to display and to memorize.

    public static final String NAME_FILE_MEMORISATION = "MoodTracker_Memory" ;

    private MoodList mMoodsPossible;

    private float mYWhenDown, mSlideSens;
    private int mCurrentPosition;

    // Variables for the managing of sound
    private SoundPool mSound;
    private int mSoundId, mResId;
    private boolean mSoundEnabled;

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

        mMoodsPossible = new MoodList();

        // Plugging the elements of the main screen
        mMoodScreen = new ArrayList<>();
        for (int i = 0; i < NUMBER_MOOD; ++i) {
            mMoodScreen.add((ImageView) findViewById(mMoodsPossible.getMoodId(i)));
        }

        mAdd_Comment = findViewById(R.id.Add_comment);
        mHistory = findViewById(R.id.Show_mood_week);
        mSoundOn = findViewById(R.id.Sound);
        mSoundOff = findViewById(R.id.No_sound);

        mValidateComment = findViewById(R.id.Add_comment_validate_btn);
        mEraseComment = findViewById(R.id.Add_comment_erase_btn);
        mComment = findViewById(R.id.Add_comment_view);
        mComment_Complement = findViewById(R.id.Add_comment_complement);

        // Set the interactives objects on listening position
        mAdd_Comment.setOnClickListener(this);
        mHistory.setOnClickListener(this);
        mValidateComment.setOnClickListener(this);
        mEraseComment.setOnClickListener(this);
        mSoundOn.setOnClickListener(this);
        mSoundOff.setOnClickListener(this);

        // Initialization of the data for the slide management
        mYWhenDown = 0.0f;
        mSlideSens = 0.0f;

        // Identifying the pressed button
        mAdd_Comment.setTag(0);
        mValidateComment.setTag(1);
        mEraseComment.setTag(2);
        mHistory.setTag(3);
        mSoundOn.setTag(4);
        mSoundOff.setTag(5);

        // Plugging to the memory space
        SharedPreferences moodsMemorized =
                getSharedPreferences(NAME_FILE_MEMORISATION, MODE_PRIVATE);
        mMemo = new Memorisation(moodsMemorized);

        // Initialization of the managing of the sound
        mSound = new SoundPool(1, USAGE_MEDIA, 0);
        mSoundId = 0;
        mResId = 0;
        mSoundEnabled = mMemo.getSoundStatus();
        soundButtonEnabled(mSoundEnabled);

        /* Initialization of the current mood
          If the current day is tomorrow(or later) relative to the memorized day,
          the moods are shifted of a day (the sixth day become the seventh, the
          fifth become the sixth, etc. and the current mood is shift to the happy mood.
          Else the current mood is the mood memorized.
         */
        mCurrentMood = mMemo.initializationOfTheMood();
        mCurrentPosition = mCurrentMood.getMoodIndex();
        setCurrentSliderItem();
    }

    private void setCurrentSliderItem() {
        ImageView mainSlideView = mMoodScreen.get(mCurrentPosition);

        // Display the current view with all the buttons
        mainSlideView.setVisibility(View.VISIBLE);
        mainSlideView.bringToFront();
        mAdd_Comment.bringToFront();
        mHistory.bringToFront();
        mSoundOn.bringToFront();
        mSoundOff.bringToFront();

        // Preparing the view
        mMemo.setMemorisationCurrentIndex(mCurrentPosition);
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, mSlideSens, Animation.RELATIVE_TO_PARENT,
                0.0f);
        slide.setDuration(1000);

        // Managing the sound correlate with the change of slide
        if (mSoundEnabled) {
            mSound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    mSound.play(mSoundId, 1.0f, 1.0f, 10,
                            0, 1.0f);
                }
            });
            if (mResId != 0) {
                mSoundId = mSound.load(MainActivity.this, mResId, 1);
            }
        }

        // Launching the sliding
        mainSlideView.startAnimation(slide);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYWhenDown = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (event.getY() < mYWhenDown) {
                    mSlideSens = 1.0f;
                    if (mCurrentPosition < NUMBER_MOOD - 1) {
                        // Only if the sound is on
                        if (mSoundEnabled)
                        // Choice of the sound correlate with the upside change of mood
                        {
                            mResId = mMoodsPossible.getMoodSoundUp(mCurrentPosition);

                        }
                        // Realization of the upside change of slide
                        ++mCurrentPosition;
                        setCurrentSliderItem();
                    }
                } else if (event.getY() > mYWhenDown) {
                    mSlideSens = -1.0f;
                    if (mCurrentPosition > 0) {
                        // Only if the sound is on
                        if (mSoundEnabled) {
                            // Choice of the sound correlate with the downside change of mood
                            mResId = mMoodsPossible.getMoodSoundDown(mCurrentPosition);
                        }
                        // Realization of the downside change of slide
                        --mCurrentPosition;
                        setCurrentSliderItem();
                    }
                }
                break;
            default:
                return super.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v)
    {
        int buttonIndex = (int) v.getTag();
        switch (buttonIndex)
        {
            case 0:
                // Activation of the comment window
                mComment.bringToFront();
                mComment.setVisibility(View.VISIBLE);
                mComment_Complement.bringToFront();
                mComment_Complement.setVisibility(View.VISIBLE);
                mValidateComment.bringToFront();
                mValidateComment.setVisibility(View.VISIBLE);
                mEraseComment.bringToFront();
                mEraseComment.setVisibility(View.VISIBLE);

                // Avoid a another try of writing a another comment or to go to history
                mAdd_Comment.setEnabled(false);
                mHistory.setEnabled(false) ;

                // Avoid to erase or to validate a previous comment
                mComment.setEnabled(true);
                mEraseComment.setEnabled(true);
                mValidateComment.setEnabled(true);

                // Loading the previous comment, if it exists, and displaying it
                String tempString = mCurrentMood.getMoodComment();
                mComment.setText(tempString);

                // Plugging the listener of the comment window
                mComment.addTextChangedListener(new TextWatcher()
                {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count,
                                                int after)
                  {
                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before,
                                            int count)
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
                // Memorisation of the comment
                tempString = mComment.getText().toString();
                closeAddComment(tempString);
            break;

            case 2 :
                // Erasing the comment
                tempString = "" ;
                closeAddComment(tempString);
            break;

            case 3 :
                // Starting the WeekHistory activity
                Intent historyActivity = new Intent(MainActivity.this,
                        WeekHistory.class);
                startActivity(historyActivity);
            break ;
            case 4:
                // Putting off the sound
                mSoundEnabled = false;
                soundButtonEnabled(false);
                mMemo.setSoundStatus(false);
                break;
            case 5:
                //Putting on the sound
                mSoundEnabled = true;
                soundButtonEnabled(true);
                mMemo.setSoundStatus(true);
                break;
        }
    }

    /**
     * Ending the comment managing
     *
     * @param comment
     */
    private void closeAddComment(String comment) {
        // Memorisation of the comment
        mCurrentMood.setMoodComment(comment);
        mMemo.setMemorisationCurrentComment(comment);

        // Deactivation of the comment window
        mComment.setVisibility(View.INVISIBLE);
        mComment.setEnabled(false);
        mComment_Complement.setVisibility(View.INVISIBLE);
        mValidateComment.setVisibility(View.INVISIBLE);
        mValidateComment.setEnabled(false);
        mEraseComment.setVisibility(View.INVISIBLE);
        mEraseComment.setEnabled(false);

        /* New activation of the buttons for writing a comment, and for activing the
         week history.
        */
        mAdd_Comment.setEnabled(true);
        mHistory.setEnabled(true);
    }

    /**
     * Managing the sound buttons when the sound is on or off
     */
    private void soundButtonEnabled(boolean soundEnabled) {
        if (soundEnabled) {
            mSoundOn.setVisibility(View.VISIBLE);
            mSoundOn.setEnabled(true);
            mSoundOff.setVisibility(View.INVISIBLE);
            mSoundOff.setEnabled(false);
        } else {
            mSoundOn.setVisibility(View.INVISIBLE);
            mSoundOn.setEnabled(false);
            mSoundOff.setVisibility(View.VISIBLE);
            mSoundOff.setEnabled(true);
        }
    }

    /**
     * Memorisation of the current state of mood, to manage a direction change of the
     * device.
     * @param outState
     */
    protected void onSaveInstanceState(Bundle outState) {
        outState.getInt(BUNDLE_STATE_MOOD_INDEX, mCurrentPosition);
        outState.getBoolean(BUNDLE_STATE_SOUND_ON, mSoundEnabled);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
    }
}
