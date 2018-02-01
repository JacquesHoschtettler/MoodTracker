package com.hoschtettler.jacques.moodtracker.model;

/**
 *
 * @author jacques
 * on 30/01/18.
 * @version 1
 * Part of the MoodTracker's model.
 * Create the frame of a mood.
 * A mood is defined by :
 *          its icon : a String ;
 *          its color : a String.
 *          its comment : a String
 */

public class Mood
{
    private String mIcon ;      //  the reference to the icon of the mood
    private String mColor ;     //  the reference to the color of the mood
    private String mComment ;   //  the eventual comment

    /**
     * Default constructor
     * By default the mood is the normal mood, without comment.
     */
    public Mood()
    {
        this.mIcon ="smiley_normal" ;
        this.mColor = "cornflower_blue_65" ;
        this.mComment ="" ;
    }

    /**
     * General constructor
     * @param icon
     * @param color
     */
    public Mood(String icon, String color)
    {
        this.mIcon = icon ;
        this.mColor = color ;
        this.mComment = "" ;
    }

    /**
     * Alternative constructor with a not empty comment
     * @param icon
     * @param color
     * @param comment
     */
    public Mood(String icon, String color, String comment)
    {
        this.mIcon = icon ;
        this.mColor = color ;
        this.mComment = comment ;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }
}
