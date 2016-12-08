package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by David Stuart
 * S1313657
 */
public class PoELogo extends Fragment {

    private SurfaceView surfaceView;

    public PoELogo()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //add canvas to this fragment
        surfaceView = new canvasSurface(getContext());
        return  surfaceView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        surfaceView = new canvasSurface(getContext());
    }
}
