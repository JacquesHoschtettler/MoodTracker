package com.hoschtettler.jacques.moodtracker.model;

/**
 *
 * @author jacques
 * on 30/01/18.
 * Part of the MoodTracker's model.
 * Create the frame of a mood.
 * A mood is defined by :
 *          its icon : a String ;
 *          its color : a String.
 */

public class Mood
{
    private String mIcon ;      //  the reference to the icon of the mood
    private String mColor ;     //  the reference to the color of the mood

    /**
     * Default constructor
     * By default the mood is the normal mood.
     */
    public Mood()
    {
        this.mIcon ="smiley_normal" ;
        this.mColor = "cornflower_blue_65" ;
    }

    /**
     * General constructor
     * @param
     *          icon
     *          color
     */
    public Mood(String icon, String color)
    {
        this.mIcon = icon ;
        this.mColor = color ;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }
}
