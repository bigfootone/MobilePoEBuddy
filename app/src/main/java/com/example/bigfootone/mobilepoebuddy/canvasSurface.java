package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Bigfootone on 07/12/2016.
 */

public class canvasSurface extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;

    canvasThread drawingThread = null;

    public canvasSurface(Context context)
    {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        drawingThread = new canvasThread(getHolder(), this);
        setFocusable(true);
    }

    public canvasThread getThread()
    {
        return drawingThread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        drawingThread.setRunning(true);
        drawingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        drawingThread.setSurfaceSize(width,height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        drawingThread.setRunning(false);
        while(retry)
        {
            try
            {
                drawingThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
