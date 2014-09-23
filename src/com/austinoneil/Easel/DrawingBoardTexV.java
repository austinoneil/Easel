package com.austinoneil.Easel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;

import java.util.ArrayList;

/**
 * Created by austin on 9/20/14.
 */
public class DrawingBoardTexV extends TextureView
{
    float touchedX;
    float touchedY;
    boolean isTouched;
    ArrayList<Circle> shapes=new ArrayList<Circle>();
    Bitmap currentScreen;
    float diameter=10;
    Paint p;

    public DrawingBoardTexV(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        touchedX=e.getX();
        touchedY=e.getY();
        isTouched=true;
        shapes.add(new Circle(touchedX, touchedY, diameter, p));
        if(e.getActionMasked()==MotionEvent.ACTION_UP) {
            ///    polyLines.add(shapes);
            shapes.clear();
            currentScreen=this.getDrawingCache();
            Canvas a=new Canvas();
            //a.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // a.drawBitmap(currentScreen, this.getLeft(), this.getTop(), null);
            ;
        }
        return true;
    }
}
