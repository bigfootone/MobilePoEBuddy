package com.example.bigfootone.mobilepoebuddy;

import android.Manifest;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    FragmentManager dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            UniqueItemDatabaseManager.dbCreate(this);
        }
        catch (IOException e)
        {
            Log.e("Tag1", "Error creating database");
        }


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PoE Buddy");


        viewPager = (ViewPager)findViewById(R.id.viewPager);
        SwipeViewAdapter swipeViewAdapter = new SwipeViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeViewAdapter);
        dialog = this.getFragmentManager();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.aboutButton:
                DialogFragment aboutDlg = new aboutDialogue();
                aboutDlg.show(dialog, "menu");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
