package com.example.bigfootone.mobilepoebuddy;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bigfootone on 17/11/2016.
 */

public class XMLParser {

    public ArrayList <SingleDevPost> allPosts = new ArrayList<>();
    private SingleDevPost devDataItem = new SingleDevPost();


    public void setDevDataItem (String sItemData)
    {
        devDataItem.setPostTitle(sItemData);
        devDataItem.setPostContent(sItemData);
        devDataItem.setPostLink(sItemData);
        devDataItem.setPostPubDate(sItemData);
    }

    public SingleDevPost getDevDataItem()
    {
        return this.devDataItem;
    }

    public ArrayList<SingleDevPost> getAllPosts()
    {
        return this.allPosts;
    }

    public XMLParser()
    {
        this.devDataItem = new SingleDevPost();
        setDevDataItem(null);
    }


    public void xmlParseItem(XmlPullParser parser, int theEventType)
    {
        try
        {

            while (theEventType != XmlPullParser.END_DOCUMENT)
            {
                if(theEventType == XmlPullParser.START_TAG)
                {
                    if(parser.getName().equalsIgnoreCase("item"))
                    {
                        devDataItem = new SingleDevPost();
                    }
                    else
                        if(parser.getName().equalsIgnoreCase("title"))
                        {
                            String temp = parser.nextText();
                            devDataItem.setPostTitle(temp);
                        }
                    else
                        if(parser.getName().equalsIgnoreCase("link"))
                        {
                            String temp = parser.nextText();
                            devDataItem.setPostLink(temp);
                        }
                    else
                        if(parser.getName().equalsIgnoreCase("pubDate"))
                        {
                            String temp = parser.nextText();
                            devDataItem.setPostPubDate(temp);
                        }
                    else
                        if(parser.getName().equalsIgnoreCase("description"))
                        {
                            String temp = parser.nextText();
                            devDataItem.setPostContent(temp);
                            allPosts.add(devDataItem);
                        }
                }
                theEventType = parser.next();
            }
        }

        catch(XmlPullParserException parserEXP1)
        {
            Log.e("Tag1", "Parsing error" + parserEXP1.toString());
        }
        catch (IOException parserEXP1)
        {
            Log.e("Tag1", "IO error during parsing");
        }
    }


    public void xmlParse (String itemsToParse) throws MalformedURLException
    {
        URL url = new URL(itemsToParse);
        InputStream inputStream;

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            String xmlRSS = getStringFromInputStream(getInputStream(url), "UTF-8");
            parser.setInput(new StringReader(xmlRSS));
            int eventType = parser.getEventType();

            xmlParseItem(parser, eventType);

        }

        catch (XmlPullParserException ae1)
        {
            Log.e("Tag1", "Parsing error" + ae1.toString());
        }

        catch (IOException ae1)
        {
            Log.e("Tag1", "IO error during parsiung");
        }

        Log.e("Tag1", "End document");
    }



    public InputStream getInputStream (URL url) throws IOException
    {
        return url.openConnection().getInputStream();
    }

    public static String getStringFromInputStream(InputStream stream, String charsetName) throws IOException
    {
        int n = 0;
        char[] buffer = new char[1024* 4];
        InputStreamReader reader = new InputStreamReader(stream, charsetName);
        StringWriter writer = new StringWriter();
        while( -1 != (n= reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }

}
