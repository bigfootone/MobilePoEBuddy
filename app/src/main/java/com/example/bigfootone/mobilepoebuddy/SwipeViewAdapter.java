package com.example.bigfootone.mobilepoebuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by David Stuart on 15/11/2016.
 * S1313657
 */

public class SwipeViewAdapter extends FragmentStatePagerAdapter {


    public SwipeViewAdapter(FragmentManager fm) {
        super(fm);
    }

    //change fragment
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
            case 3:
                return new PoELogo();
        }
        return null;
    }

    //number of fragments
    @Override
    public int getCount()
    {
        return 4;
    }

    //set fragment name
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
            case 3:
                return "PoE Logo";
        }
        return null;
    }
}
