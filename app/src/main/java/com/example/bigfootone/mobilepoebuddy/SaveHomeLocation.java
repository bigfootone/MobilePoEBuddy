package com.example.bigfootone.mobilepoebuddy;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.google.android.gms.maps.MapView;

/**
 * Created by David Stuart on 05/12/2016.
 * S1313657
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

    public SaveHomeLocation(SharedPreferences preferences)
    {
        setLatitude(1);
        setLongitude(1);
        try
        {
            this.sharedPrefs = preferences;
        }
        catch (Exception e)
        {
            Log.e("pref", "Save failed to load");
        }
    }


    public void savePreferences(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
