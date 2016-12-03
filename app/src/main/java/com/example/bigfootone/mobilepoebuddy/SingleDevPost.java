package com.example.bigfootone.mobilepoebuddy;

import java.io.Serializable;

/**
 * Created by Bigfootone on 17/11/2016.
 */

public class SingleDevPost implements Serializable{

    private String postTitle;
    private String postLink;
    private String postPubDate;
    private String postContent;
    private String postPoster;
    private String[] splitTitle;
    private String[] postPosterSplit;

    public String getPostTitle()
    {
        return this.postTitle;
    }

    public String getPostLink()
    {
        return this.postLink;
    }

    public String getPostPubDate()
    {
        return this.postPubDate;
    }

    public String getPostContent()
    {
        return this.postContent;
    }

    public String getPostPoster()
    {
        return this.postPoster;
    }

    public void setPostTitle(String sPostTitle)
    {
        this.postTitle = sPostTitle;
    }

    public void setPostLink(String sPostLink)
    {
        this.postLink = sPostLink;
    }

    public void setPostPubDate(String sPostPubDate)
    {
        this.postPubDate = sPostPubDate;
    }

    public void setPostContent(String sPostContent)
    {
        this.postContent = sPostContent;
    }

    public void setPostPoster(String sPostContent) {this.postPoster = sPostContent; }

    public SingleDevPost()
    {
        this.postTitle = "";
        this.postPubDate = "";
        this.postLink = "";
        this.postContent = "";
        this.postPoster = "";
    }

    public void splitTitle()
    {
        splitTitle = postTitle.split("(?<= - )");
        postTitle = "";
        for(int i = 1; i < splitTitle.length; i++)
        {
            postTitle = postTitle + splitTitle[i];
        }

        postPosterSplit = splitTitle[0].split(" - ");
        for(int i = 0; i < postPosterSplit.length; i++)
        {
            postPoster = postPoster + postPosterSplit[i];
        }
    }

}
