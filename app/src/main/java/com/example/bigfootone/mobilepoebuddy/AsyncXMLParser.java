package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Bigfootone on 22/11/2016.
 */

public class AsyncXMLParser extends AsyncTask<String, Integer, ArrayList<SingleDevPost>> {

    private String urlToParse;
    private Context appContext;
    public ArrayList <SingleDevPost> singleDevPostsArray = new ArrayList<>();

    public AsyncXMLParser(Context currentContext, String url)
    {
        urlToParse = url;
        appContext = currentContext;
    }

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(appContext, "Started parsing", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ArrayList<SingleDevPost> doInBackground(String... params)
    {
        XMLParser xmlParser = new XMLParser();
        try
        {
            xmlParser.xmlParse(urlToParse);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        singleDevPostsArray = xmlParser.getAllPosts();
        return singleDevPostsArray;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList)
    {
        Toast.makeText(appContext, "Finished parsing", Toast.LENGTH_SHORT).show();
    }
}
