package com.hoschtettler.jacques.moodtracker.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author jacques
 * on 30/01/18.
 * @version 2
 * Part of the MoodTracker's model.
 * Create the frame of a mood.
 * A mood is defined by, in this order :
 *          its icon : a String ;
 *          its color : a String.
 *          its comment : a String
 */

public class Mood
{
    /*  The three strings are in a linked set */
    private Set<String> mMood = new LinkedHashSet<>() ;

    /**
     * Default constructor
     * By default the mood is the normal mood, without comment.
     */
    public Mood()
    {
        this.mMood.add("smiley_normal") ;
        this.mMood.add("cornflower_blue_65") ;
        this.mMood.add("") ;
    }

    /**
     * General constructor
     * @param icon
     * @param color
     */
    public Mood(String icon, String color)
    {
        this.mMood.add(icon) ;
        this.mMood.add(color) ;
        this.mMood.add("") ;
    }

    /**
     * Alternative constructor with a not empty comment
     * @param icon
     * @param color
     * @param comment
     */
    public Mood(String icon, String color, String comment)
    {
        this.mMood.add(icon) ;
        this.mMood.add(color) ;
        this.mMood.add(comment) ;
    }

    /**
     *  Returns the name of the icon
     */
    public String getIcon()
    {
        return (String)mMood.toArray()[0] ;
    }

    /**
     * Put a new name of icon
     * @param icon
     */
    public void setIcon(String icon)
    {
        String[] tempMood = (String[]) mMood.toArray() ;
        tempMood[0] = icon ;
        addToMood(tempMood);
    }

    /**
     * Returns the name of the color
     */
    public String getColor()
    {
        return (String)mMood.toArray()[1];
    }

    /**
     * Put a new name of the color
     * @param color
     */
    public void setColor(String color)
    {
        String[] tempMood = (String[]) mMood.toArray() ;
        tempMood[1] = color ;
        addToMood(tempMood);
    }

    /**
     *  Return the comment
     */
    public String getComment()
    {
        return(String)mMood.toArray()[3];
    }

    /**
     * Put a new comment
     * @param comment
     */
    public void setComment(String comment)
    {
        String[] tempMood = (String[]) mMood.toArray() ;
        tempMood[2] = comment ;
        addToMood(tempMood); ;
    }

    /**
     * Private method to rebuild the linked chain of the set
     * Used by setIcon, setColor and set Comment
     */
    private void addToMood(String[] tempMood)
    {
        this.mMood.clear() ;
        this.mMood.add(tempMood[0]) ;
        this.mMood.add(tempMood[1]) ;
        this.mMood.add(tempMood[2]) ;
    }

}
