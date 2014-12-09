package com.example.alex.customnewsfeedproject;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Alex on 12/3/2014.
 */
public class ArticleScrapper extends AsyncTask<RecyclerViewAdapter, Void, List<List<Articles>>> {

    private List<URL> rssWebsites;
    private List<String> keywords;
    private ArticleSAXHandler handler;
    private XMLReader xr;
    private List<List<Articles>> allArticles = new ArrayList<List<Articles>>();
    private RecyclerViewAdapter rA;

    public ArticleScrapper() throws MalformedURLException {
        Log.e("Article", "Aynctask created..................");
        rssWebsites = new ArrayList<URL>();
        URL cnn = new URL("http://rss.cnn.com/rss/cnn_latest.rss");
        URL espn = new URL("http://sports.espn.go.com/espn/rss/news");
        rssWebsites.add(cnn);
        rssWebsites.add(espn);
        keywords = new ArrayList<String>();
    }

    @Override
    protected List<List<Articles>> doInBackground(RecyclerViewAdapter... params) {
        Log.e("Article", "Asynctask begun....");
        rA = params[0];
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = null;
        try {
            saxParser = spf.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        XMLReader xr = null;
        try {
            xr = saxParser.getXMLReader();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        handler = new ArticleSAXHandler();
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);
        for (URL u: rssWebsites)
        {
            try {
                xr.parse(new InputSource(new InputStreamReader(u.openStream())));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            allArticles.add(handler.getArticles());
        }
        return allArticles;
    }

    @Override
    protected void onPostExecute(List<List<Articles>> result)
    {
        LinkedList<Articles> tempList = combineLists(result);
        rA.addItems(tempList);

    }

    private LinkedList<Articles> combineLists(List<List<Articles>> list)
    {
        LinkedList<Articles> temp = new LinkedList<Articles>();
        for (List<Articles> list2: allArticles)
        {
            for(Articles a: list2)
            {
                temp.add(a);
            }
        }
        return temp;
    }

}
