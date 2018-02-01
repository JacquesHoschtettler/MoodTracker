package com.hoschtettler.jacques.moodtracker.model;

import java.util.ArrayList;

/**
 * Created by
 * @author jacques
 * on 30/01/18.
 * @version 1
 * Part of the MoodTracker's model.
 * Define the list of the moods, the mood with index 0 is the sadest one, the mood with index 4
 * is the happiest one.
 */

public class MoodList extends Mood
{
    private ArrayList<Mood> mMoods = new ArrayList() ;

    /**
     * Create the list of moods
     * This is the unique constructor
     * @see Mood
     */
    public MoodList()
    {
        mMoods.add(new Mood("smiley_sad","faded_red")) ;                // sad
        mMoods.add(new Mood("smiley_disappointed","warm_grey")) ;       // disappointed
        mMoods.add(new Mood("smiley_normal","cornflower_blue_65")) ;    // normal
        mMoods.add(new Mood("smiley_happy","light_sage")) ;             // happy
        mMoods.add(new Mood("smiley_super_happy","banana_yellow")) ;    // very happy
    }

    /**
     * The unique getter to return the mood with its number of order
     * @see Mood
     * @param index
     *          the number of the mood :
     *          0 for "smiley_sad" ;
     *          1 for "smiley_disappointed" ;
     *          2 for "smiley_normal" ;
     *          3 for "smiley_happy" ;
     *          4 for "smiley_super_happy".
     * @return mood
     *          the mood correlating with the index (see upper)
     */
    public Mood getMoods(int index)
    {
        return mMoods.get(index) ;
    }
}
