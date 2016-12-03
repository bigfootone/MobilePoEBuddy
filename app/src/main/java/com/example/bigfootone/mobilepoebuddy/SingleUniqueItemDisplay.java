package com.example.bigfootone.mobilepoebuddy;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Bigfootone on 02/12/2016.
 */

public class SingleUniqueItemDisplay extends AppCompatActivity
{

    public TextView uniqueName;
    public TextView uniqueBaseType;
    public TextView uniqueFlavourText;
    public TextView uniqueArmourValue;
    public TextView uniqueEvasionValue;
    public TextView uniqueESValue;
    public TextView uniqueDescription;
    public TextView armourText;
    public TextView evasionText;
    public TextView esText;
    public ImageView image;
    public String itemName;
    public String itemFlavourText;
    public String itemDescription;
    public String itemSubCategory;
    public Integer endRow;
    public String imageLink;
    public ImageView favouritesButton;
    public Integer ID;
    public boolean favourite;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unique_item_layout);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(itemName);
        actionBar.setDisplayHomeAsUpEnabled(true);

        uniqueName = (TextView) findViewById(R.id.UniqueName);
        uniqueBaseType = (TextView) findViewById(R.id.UniqueBaseType);
        uniqueFlavourText = (TextView) findViewById(R.id.UniqueFlavourText);
        uniqueArmourValue = (TextView) findViewById(R.id.UniqueArmourValue);
        uniqueEvasionValue = (TextView) findViewById(R.id.UniqueEvasionValue);
        uniqueESValue = (TextView) findViewById(R.id.UniqueESValue);
        uniqueDescription = (TextView) findViewById(R.id.UniqueDescription);
        armourText = (TextView) findViewById(R.id.UniqueArmourTitle);
        evasionText = (TextView) findViewById(R.id.UniqueEvasionTitle);
        esText = (TextView) findViewById(R.id.UniqueESTitle);
        image = (ImageView) findViewById(R.id.uniqueImageView);
        favouritesButton = (ImageView) findViewById(R.id.favouritesButton);

        Intent intent = getIntent();
        String uniqueItemName = (String) intent.getSerializableExtra("name");
        endRow = (Integer) intent.getSerializableExtra("NumberOfRows");

        final UniqueItemDatabaseManager databaseManager = new UniqueItemDatabaseManager(getApplicationContext(), "UniqueItemDB.s3db", null, 1);
        SingleUniqueItem singleUniqueItem = databaseManager.findItemNameSearch(uniqueItemName);
        itemName = singleUniqueItem.getItemUniqueName();
        itemFlavourText = singleUniqueItem.getItemFlavourText();
        itemDescription = singleUniqueItem.getItemDescription();
        itemSubCategory = singleUniqueItem.getItemSubCategory();
        ID = singleUniqueItem.getItemID();
        favourite = singleUniqueItem.getItemFavourite();
        Log.e("Tag",Boolean.toString(favourite));

        if(favourite)
        {
            favouritesButton.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else
        {
            favouritesButton.setImageResource(android.R.drawable.btn_star_big_off);
        }

        imageLink = singleUniqueItem.getItemImageLink();
        int imageID = getResources().getIdentifier(imageLink, "drawable" ,getPackageName());
        Drawable actualImage = getResources().getDrawable(imageID);

        uniqueBaseType.setText(singleUniqueItem.getItemBaseType());
        uniqueArmourValue.setText(singleUniqueItem.getItemArmourValue());
        uniqueEvasionValue.setText(singleUniqueItem.getItemEvasionValue());
        uniqueESValue.setText(singleUniqueItem.getItemEnergyShieldValue());

        splitText(itemFlavourText, itemDescription);

        uniqueName.setText(itemName);
        uniqueFlavourText.setText(itemFlavourText);
        uniqueDescription.setText(itemDescription);
        image.setImageDrawable(actualImage);

        GradientDrawable borders = new GradientDrawable();
        borders.setCornerRadius(0);
        borders.setStroke(5, Color.BLACK);
        uniqueArmourValue.setBackground(borders);
        uniqueEvasionValue.setBackground(borders);
        uniqueESValue.setBackground(borders);
        armourText.setBackground(borders);
        evasionText.setBackground(borders);
        esText.setBackground(borders);
        uniqueDescription.setBackground(borders);

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                databaseManager.addToFavourites(ID, true);
            }
        });
    }

    public void splitText(String flavourText, String description)
    {
        flavourText = itemFlavourText.replaceAll("<br>", "\n");
        description = itemDescription.replaceAll("<br>", "\n");
        this.itemDescription = description;
        this.itemFlavourText = flavourText;

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(getApplicationContext(), SearchedDatabasedActivity.class);
        intent.putExtra("subCategory", itemSubCategory);
        intent.putExtra("NumberOfRows", endRow);
        startActivityForResult(intent, 0);
        return true;
    }

}