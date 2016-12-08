package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by David Stuart on 29/11/2016.
 * S1313657
 */

public class AsyncImageLoader extends AsyncTask <String, Void, Bitmap>{

    ImageView parsedImage;

    public AsyncImageLoader(ImageView image)
    {
        this.parsedImage = image;
    }

    //load images in a seperate thread
    @Override
    protected Bitmap doInBackground(String... urls)
    {
        String urlToDisplay = urls[0];
        Bitmap bitmap = null;
        try
        {
            InputStream stream = new java.net.URL(urlToDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(stream);
        }
        catch (Exception e)
        {
            Log.e("Tag1", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result)
    {
        parsedImage.setImageBitmap(result);
    }
}
