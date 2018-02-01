package com.hoschtettler.jacques.moodtracker.model;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author jacques
 * on 30/01/18.
 * @version 2
 * Part of the MoodTracker's model.
 * Create the frame of a mood.
 * A mood is defined by :
 *          its icon : a String ;
 *          its color : a String.
 *          its comment : a String
 */

public class Mood implements Set
{
    private Set<String> mMood = new LinkedHashSet<String> ;  // The three strings are in a set
    private String mIcon ;      //  the reference to the icon of the mood
    private String mColor ;     //  the reference to the color of the mood
    private String mComment ;   //  the eventual comment

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

    public String getIcon()
    {
        return (String)mMood.toArray()[0] ;
    }

    public void setIcon(String icon)
    {
        String[] tempMood = (String[]) mMood.toArray() ;
        tempMood[0] = icon ;
        addToMood(tempMood);
    }

    public String getColor()
    {
        return (String)mMood.toArray()[1];
    }

    public void setColor(String color)
    {
        String[] tempMood = (String[]) mMood.toArray() ;
        tempMood[1] = color ;
        addToMood(tempMood);
    }

    public String getComment()
    {
        return(String)mMood.toArray()[3];
    }

    public void setComment(String comment)
    {
        String[] tempMood = (String[]) mMood.toArray() ;
        tempMood[2] = comment ;
        addToMood(tempMood); ;
    }

    private void addToMood(String[] tempMood)
    {
        this.mMood.clear() ;
        this.mMood.add(tempMood[0]) ;
        this.mMood.add(tempMood[1]) ;
        this.mMood.add(tempMood[2]) ;
    }

}
