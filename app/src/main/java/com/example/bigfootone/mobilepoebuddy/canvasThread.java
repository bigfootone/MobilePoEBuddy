package com.example.bigfootone.mobilepoebuddy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;

/**
 * Created by David Stuart on 07/12/2016.
 * S1313657
 */

public class canvasThread extends Thread{

    private int canvasWidth;
    private int canvasHeigh;

    private float halfAppletHeigh;
    private float halfAppletWidth;

    private boolean first = true;
    private boolean run = false;

    private SurfaceHolder surfaceHolder;
    private Paint cPaint;
    private canvasSurface cSurface;


    public canvasThread(SurfaceHolder holder, canvasSurface surface)
    {
        this.surfaceHolder = holder;
        this.cSurface = surface;
        cPaint = new Paint();
    }

    public void doStart()
    {
        synchronized (cSurface)
        {
            first = false;
        }
    }

    public void run()
    {
        while (run)
        {
            Canvas c = null;
            try
            {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder)
                {
                    svDraw(c);
                }
            }
            finally
            {
                if (c != null)
                {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean b)
    {
        run = b;
    }

    public void setSurfaceSize(int width, int height)
    {
        synchronized (surfaceHolder)
        {
            canvasWidth = width;
            canvasHeigh = height;
            halfAppletHeigh = canvasHeigh / 2;
            halfAppletWidth = canvasWidth / 32;
            doStart();
        }
    }

    public void svDraw(Canvas canvas)
    {
        if(run)
        {
            canvas.save();
            canvas.restore();
            canvas.drawColor(Color.rgb(90,90,90));
            cPaint.setStyle(Paint.Style.FILL);
            cPaint.setColor(Color.BLACK);
            drawOval(canvas);
            drawCentreOrb(canvas);
            drawText(canvas);
            drawSideOrbs(canvas);
        }
    }

    public void drawOval(Canvas canvas)
    {
        RectF backgroundOval = new RectF(canvasWidth-5, (canvasHeigh/4)*3, 5,canvasHeigh/4);
        RectF backgroundOvalInside = new RectF(canvasWidth-15, ((canvasHeigh/4)*3)-10, 15,(canvasHeigh/4)-10);
        cPaint.setColor(Color.RED);
        canvas.drawOval(backgroundOval, cPaint);
        cPaint.setColor(Color.BLACK);
        canvas.drawOval(backgroundOvalInside, cPaint);
    }

    public void drawCentreOrb(Canvas canvas)
    {
        cPaint.setColor(Color.RED);
        canvas.drawCircle(canvasWidth / 2, canvasHeigh / 4, 100, cPaint);
        cPaint.setColor(Color.BLACK);
        canvas.drawCircle(canvasWidth / 2, canvasHeigh / 4, 90, cPaint);
    }

    public void drawText(Canvas canvas)
    {
        cPaint.setColor(Color.WHITE);
        cPaint.setTextSize(100);
        canvas.drawText("Path of Exile", canvasWidth/4, canvasHeigh/2, cPaint);
    }

    public void drawSideOrbs(Canvas canvas)
    {
        for(int i = 1; i < 4; i++)
        {
            cPaint.setColor(Color.RED);
            canvas.drawCircle((canvasWidth/4)* i, (canvasHeigh / 7) * 2, 70, cPaint);
            cPaint.setColor(Color.BLACK);
            canvas.drawCircle((canvasWidth/4)* i, (canvasHeigh / 7) * 2, 60, cPaint);
            i++;
        }
    }

}
