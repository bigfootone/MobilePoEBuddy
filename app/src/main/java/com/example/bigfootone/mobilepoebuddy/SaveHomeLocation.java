package com.example.bigfootone.mobilepoebuddy;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Bigfootone on 05/12/2016.
 */

public class SaveHomeLocation extends Activity{

    SharedPreferences sharedPrefs;

    private float Latitude;
    private float Longitude;


    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
}
