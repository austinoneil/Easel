package com.austinoneil.Easel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.*;

/**
 * Created by austin on 9/25/14.
 */
public class DrawingBoardView extends ImageView {

    float xPrev;
    float yPrev;
    float x;
    float y;
    Paint p;
    BitmapDrawable currentBitmap;
    Bitmap background;
    boolean justGotDoneWithActionDown=false;

    OnTouchListener listener=new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Log.d("onTouch", event.getX()+","+event.getY());
            justGotDoneWithActionDown=false;
            switch (event.getActionMasked())
            {
                case MotionEvent.ACTION_DOWN:
                    xPrev=event.getX();
                    yPrev=event.getY();
                    justGotDoneWithActionDown=true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    xPrev=x;
                    yPrev=y;
                    break;
            }
            x=event.getX();
            y=event.getY();
            invalidate();
            return true;
        }


    };

    public DrawingBoardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        super.setOnTouchListener(listener);
       /* Bitmap bmp=Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        this.setImageBitmap(bmp);
        c=new Canvas(bmp);*/

        p=new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(10);
    }

    public void setColor(int c)
    {
        p.setColor(c);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("onDraw", "in onDraw");
        if(background==null)
            background=Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas imageCanvas=new Canvas(background);
        //singleUseCanvas.setBitmap(background);
        //canvas.drawBitmap(background, this.getLeft(), this.getTop(), null);

            canvas.drawBitmap(background, 0, this.getTop(), null);
        if(!justGotDoneWithActionDown) {
            canvas.drawCircle(xPrev, yPrev, 5, p);
            canvas.drawLine(xPrev, yPrev, x, y, p);
            canvas.drawCircle(x, y, 5, p);
        }

            imageCanvas.drawBitmap(background, 0, this.getTop(), null);
        if(!justGotDoneWithActionDown)
        {
            imageCanvas.drawCircle(xPrev, yPrev, 5, p);
            imageCanvas.drawLine(xPrev, yPrev, x, y, p);
            imageCanvas.drawCircle(x, y, 5, p);
        }
    }

    public Bitmap loadBitmapFromView()
    {
        return background;
    }
}
