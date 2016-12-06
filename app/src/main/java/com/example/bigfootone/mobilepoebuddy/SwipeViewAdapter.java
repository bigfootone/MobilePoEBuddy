package com.example.bigfootone.mobilepoebuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Bigfootone on 15/11/2016.
 */

public class SwipeViewAdapter extends FragmentStatePagerAdapter {

    public SwipeViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new DevPostsFragment();
            case 1:
                return new UniqueItemFragment();
            case 2:
                return new MapActivity();
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case 0:
                return "Dev Tracker";
            case 1:
                return "Uniques";
            case 2:
                return "Maps";
        }
        return null;
    }
}
