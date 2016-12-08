package com.example.bigfootone.mobilepoebuddy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class devPostActivity extends AppCompatActivity {

    public String poster;
    public String content;
    public String link;
    public String linkActualText;
    public String[] contentSplit;
    String [][] responseList = new String[5][10];
    Integer currentComment = 0;
    Integer arrayUser = 0;
    Integer arrayComment = 1;
    Integer arrayIsQuote = 2;
    Integer arrayInQuote = 3;
    Integer arrayImage = 4;
    public Integer baseColour = 90;
    public Integer colourRedcution = 20;
    public boolean commentChain = false;
    public int chainReference = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //create action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PoE Buddy");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //create intent and receive information about the post that was clicked
        Intent intent = getIntent();
        SingleDevPost devPost = (SingleDevPost) intent.getSerializableExtra("postClicked");
        poster = devPost.getPostPoster();
        content = devPost.getPostContent();
        link = devPost.getPostLink();
        linkActualText = "<a href = '"+ link + "'> View in browser. </a>";


        Log.e("Tag1", content);


        splitContent();

        //setting up the scrollview
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.rgb(90,90,90));
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setPadding(50,40,50,40);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);

        //setting up parameters for comment/main post
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(50, 40, 50, 40);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams2.setMargins(30, 20, 30, 20);


        //adding every comment to the scrollview
        for (int i = 0; i <= currentComment; i++)
        {

            //grabbing username and formatting
            TextView devName = new TextView(this);
            String userNameText = responseList[arrayUser][i];
            devName.setText(userNameText + " wrote: ");
            devName.setTextColor(Color.rgb(200,200,200));

            //grabbing single comment and formatting
            TextView postContent = new TextView(this);
            String userPostText = responseList[arrayComment][i];
            splitContent(userPostText);
            postContent.setText(content);
            postContent.setTextColor(Color.rgb(200,200,200));

            //if comment is a quote
            if(responseList[arrayIsQuote][i] == "1")
            {

                LinearLayout singleCommentLayout = new LinearLayout(this);
                devName.setLayoutParams(layoutParams);
                postContent.setLayoutParams(layoutParams);

                //if comment is in a quote
                if(responseList[arrayInQuote][i] != "1")
                {
                    singleCommentLayout.setOrientation(LinearLayout.VERTICAL);
                    singleCommentLayout.setBackgroundColor(Color.rgb(baseColour - colourRedcution,baseColour - colourRedcution,baseColour - colourRedcution));
                    singleCommentLayout.addView(devName);
                    singleCommentLayout.addView(postContent);
                    linearLayout.addView(singleCommentLayout);
                }
                //if comment is not in a quote
                else
                {
                    singleCommentLayout.setOrientation(LinearLayout.VERTICAL);
                    singleCommentLayout.setBackgroundColor(Color.rgb(baseColour - colourRedcution,baseColour - colourRedcution,baseColour - colourRedcution));
                    singleCommentLayout.addView(devName);
                    singleCommentLayout.addView(postContent);
                    linearLayout.addView(singleCommentLayout);
                }

                //check for images and add
                if(responseList[arrayImage][i] != null)
                {
                    ImageView imageView = new ImageView(this);
                    new AsyncImageLoader(imageView).execute(responseList[arrayImage][i]);
                    imageView.setLayoutParams(layoutParams2);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    singleCommentLayout.addView(imageView);
                }

                //add a border to the textview
                GradientDrawable borders = new GradientDrawable();
                borders.setCornerRadius(20);
                borders.setStroke(5, Color.YELLOW);
                borders.setColor(Color.rgb(baseColour - 20,baseColour - 20,baseColour - 20));
                singleCommentLayout.setBackground(borders);
            }
            //comment is not a quote
            else
            {

                devName.setLayoutParams(layoutParams2);
                postContent.setLayoutParams(layoutParams2);
                GradientDrawable borders = new GradientDrawable();
                borders.setCornerRadius(20);
                borders.setStroke(5, Color.YELLOW);
                borders.setColor(Color.rgb(baseColour,baseColour,baseColour));
                linearLayout.setBackground(borders);

                linearLayout.addView(devName);
                linearLayout.addView(postContent);


                if(responseList[arrayImage][i] != null)
                {
                    ImageView imageView = new ImageView(this);
                    new AsyncImageLoader(imageView).execute(responseList[arrayImage][i]);
                    imageView.setLayoutParams(layoutParams2);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    linearLayout.addView(imageView);
                }
            }

        }

        //add link to original page at the end of the view
        TextView postLink = new TextView(this);
        postLink.setText(Html.fromHtml(linkActualText));
        postLink.setClickable(true);
        postLink.setMovementMethod(LinkMovementMethod.getInstance());
        postLink.setLayoutParams(layoutParams);
        postLink.setTextColor(Color.rgb(200,200,200));
        linearLayout.addView(postLink);

        this.setContentView(scrollView);
    }

    //split up comments
    public void splitContent()
    {
        //Is there quote?
        contentSplit = content.split("<.?blockquote.*?>");
        if(contentSplit.length != 1)
        {
            for(int i = 1; i < contentSplit.length; i++)
            {
                //Is it a quote from a user?
                String[] contentSplitUsers = contentSplit[i].split("profile/");
                if(contentSplitUsers.length != 1)
                {
                    //Is this a quote?
                    String[] isQuote = contentSplitUsers[0].split("span class = \"quote\">");
                    if(isQuote.length != 1)
                    {
                      responseList[arrayIsQuote][currentComment] = "0";
                    }
                    else
                    {
                        responseList[arrayIsQuote][currentComment] = "1";
                    }

                    //Get username
                    String[] getUsername = contentSplitUsers[1].split("\">");
                    String username = getUsername[0];
                    username = username.replaceAll("<.+?>", "");
                    responseList[arrayUser][currentComment] = username;

                    //Is this the end of a chain of quotes?  None of this works, its here just incase
                    String[] getUserComment = contentSplitUsers[1].split("\"bot\">");
                    if(getUserComment.length != 1)
                    {
                        String userComment = getUserComment[1];

                        String pictureSplit[] = userComment.split("img src=\"");
                        if (pictureSplit.length != 1)
                        {
                            String imageLinks[] = pictureSplit[1].split("\"");
                            responseList[arrayImage][currentComment] = imageLinks[0];
                        }

                        userComment = userComment.replaceAll("<.+?>", "");
                        responseList[arrayComment][currentComment] = userComment;
                        responseList[arrayInQuote][currentComment] = "0";
                        currentComment++;

                        if(chainReference > 0)
                        {
                            commentChain = true;
                        }

                    }
                    else
                    {
                        if (commentChain)
                        {
                            String userComment = getUserComment[0];
                            userComment = userComment.replaceAll("<.+?>", "");
                            responseList[arrayComment][currentComment - chainReference] = userComment;
                            chainReference--;
                        }
                        else
                        {
                            responseList[arrayInQuote][currentComment] = "1";
                            chainReference++;
                        }
                    }
                }
                //For Dev quotes that dont use <quote>
                else
                {
                    String userComment = contentSplitUsers[0];

                    String pictureSplit[] = userComment.split("img src=\"");
                    if(pictureSplit.length != 1)
                    {
                        String imageLinks[] = pictureSplit[1].split("\"");
                        responseList[arrayImage][currentComment] = imageLinks[0];
                    }

                    userComment = userComment.replaceAll("<.+?>", "");
                    responseList[arrayUser][currentComment] = poster;
                    responseList[arrayComment][currentComment] = userComment;
                    responseList[arrayInQuote][currentComment] = "0";
                }
            }
        }
        //If it's just a dev message
        else
        {
            String pictureSplit[] = content.split("img src=\"");
            if(pictureSplit.length != 1)
            {
                String imageLinks[] = pictureSplit[1].split("\"");
                responseList[arrayImage][currentComment] = imageLinks[0];
            }
            responseList[arrayUser][currentComment] = poster;
            responseList[arrayComment][currentComment] = content;
            responseList[arrayIsQuote][currentComment] = "0";

        }
    }

    //Formatting
    public void splitContent(String content)
    {
        content = content.replaceAll("<li>", "\n    - ");
        content = content.replaceAll("<br>", "\n");
        content = content.replaceAll("<.+?>", "");
        this.content = content;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}
