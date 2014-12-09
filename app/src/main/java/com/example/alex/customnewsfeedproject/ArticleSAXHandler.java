package com.example.alex.customnewsfeedproject;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12/3/2014.
 */
public class ArticleSAXHandler extends DefaultHandler{

    private List<Articles> articles;
    private Articles article;
    private boolean description = false;
    private boolean pubDate = false;
    private boolean title = false;
    private boolean item = false;
    private boolean itemID = false;

    public ArticleSAXHandler()
    {

    }

    public List<Articles> getArticles()
    {
        return articles;
    }
    @Override
    public void startDocument()
    {
        articles = new ArrayList<Articles>();
    }
    @Override
    public void startElement (String uri, String name, String qName, Attributes atts)
    {
        if(qName.equals("item")) {
            item = true;
            article = new Articles();
        }
        else if(qName.equals("description")) {
            if (item) {
                description = true;
            }
        }
        else if(qName.equals("title")) {
            if (item) {
                title = true;
            }
        }
        else if(qName.equals("pubDate")) {
            if (item) {
                pubDate = true;
            }
        }
        else if(qName.equals("guid")) {
            if (item) {
                itemID = true;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    {
        if (qName.equals("item"))
        {
            articles.add(article);
            item = false;
        }
        else if(qName.equals("description")) {
            description = false;
        }
        else if(qName.equals("title")) {
            title = false;
        }
        else if(qName.equals("pubDate")) {
            pubDate = false;
        }
        else if(qName.equals("guid"))
        {
            itemID = false;
        }
    }

    @Override
    public void characters (char ch[], int start, int length)
    {
        String temp = "";
        if (title)
        {
            for (int i = start; i < start + length; i++)
            {
                temp += ch[i];
            }
            article.setTitle(temp);
        }
        else if (pubDate)
        {
            for (int i = start; i < start + length; i++)
            {
                temp += ch[i];
            }
            try {
                article.setDate(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if (itemID)
        {
            for (int i = start; i < start + length; i++)
            {
                temp += ch[i];
            }
            try {
                article.setURL(temp);
            } catch (MalformedURLException e) {

                try {
                    article.setURL(article.getID()+ temp);
                    Log.e("Article URL", article.getID());
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }

                e.printStackTrace();
            }
            Log.e(article.getTitle(), article.getID());
        }
        else if (description)
        {
            for (int x = start; x < start + length; x++)
            {
                if (ch[x] == '<')
                {
                    article.setDescription(temp);
                    description = false;
                    return;
                }
                temp += ch[x];
            }
            article.setDescription(temp);
        }

    }


}
