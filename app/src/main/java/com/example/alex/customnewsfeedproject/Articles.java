package com.example.alex.customnewsfeedproject;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 11/30/2014.
 */
public class Articles implements Comparable<Articles>, Serializable {
    private String title;
    private String description;
    private Date date;
    private URL id;

    {
        try {
            id = new URL("http://www.google.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Bitmap thumbnail;
    private final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

    public Articles()
    {

    }

    public Articles(String t, String d)
    {
        title = t;
        description = d;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setDate(String date) throws ParseException {
        this.date = sdf.parse(date);
    }

    public void setURL(String id) throws MalformedURLException
    {
        this.id = new URL(id);
    }

    public void setThumbnail()
    {
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public Date getDate()
    {
        return date;
    }

    public String getID()
    {
        return id.toString();
    }

    public Bitmap getThumbnail()
    {
        return null;
    }

    @Override
    public int compareTo(Articles another) {
        return this.date.compareTo(another.getDate());
    }

}
