package com.austinoneil.Easel;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.*;
import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by austin on 9/19/14.
 */
public class DrawingBoard extends SurfaceView implements Runnable
{
    Thread t=null;
    SurfaceHolder holder;
    boolean isItOk=false;
    Bitmap currentScreen;
    Canvas c;

    Paint p;
    float diameter=10;
    boolean isTouched=false;
    float touchedX;
    float touchedY;
    float prevX;
    float prevY;
    boolean firstTouch=true;
    int drawingBoardWidth;
    int drawingBoardHeight;
    String currentShape="circle";

    ArrayList<Circle> shapes=new ArrayList<Circle>();
    ArrayList<ArrayList<Circle>> polyLines=new ArrayList<ArrayList<Circle>>();
    Canvas a;

    public DrawingBoard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);


        this.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        this.layout(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());

        super.buildDrawingCache(true);
        p=new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(2*diameter);
        holder=getHolder();
    }


    public void run()
    {
        while(isItOk) {
            if (!holder.getSurface().isValid()) {
                continue;
            }
            t.setPriority(1);

            Canvas a = holder.lockCanvas();


            a.drawLine(prevX, prevY, touchedX, touchedY, new Paint(p));

            // might need this later...
            for(int i=0; i<shapes.size(); i++)
            {
                a.drawCircle(shapes.get(i).x, shapes.get(i).y, shapes.get(i).radius, shapes.get(i).paint);
                if(i!=0 && shapes.size()>i && shapes.get(i)!=null) // checking for null because I don't know.
                {

                    try {
                        a.drawLine(shapes.get(i - 1).x,
                                shapes.get(i - 1).y,
                                shapes.get(i).x,
                                shapes.get(i).y,
                                shapes.get(i).paint
                        );
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
            //currentScreen=this.getDrawingCache();
            //a.drawBitmap(currentScreen, this.getLeft(), this.getTop(), null);
            holder.unlockCanvasAndPost(a);
            drawingBoardWidth = this.getWidth();
            drawingBoardHeight = this.getHeight();
            isTouched=false;
        }
    }

    public void pause()
    {
        isItOk=false;
        while(true)
        {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        t=null;
    }


    public void resume()
    {
        isItOk=true;
        t=new Thread(this);
        Log.d("resume", "thread about to start");
        t.start();
    }

    public void setColor(int c)
    {
        p.setColor(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        a=holder.lockCanvas();
        prevX=touchedX;
        prevY=touchedY;
        touchedX=e.getX();
        touchedY=e.getY();
        if(firstTouch)
        {
            prevX=touchedX;
            prevY=touchedY;
            firstTouch=false;
        }
        isTouched=true;
        a.drawLine(prevX, prevY, touchedX, touchedY, p);

        shapes.add(new Circle(touchedX, touchedY, diameter, p));
        if(e.getActionMasked()==MotionEvent.ACTION_DOWN)
        {
            shapes.clear();
        }
        if(e.getActionMasked()==MotionEvent.ACTION_UP) {
            polyLines.add(shapes);
            firstTouch=true;
            currentScreen=this.getDrawingCache();
            a.drawBitmap(currentScreen, this.getLeft(), this.getTop(), null);
        }


        holder.unlockCanvasAndPost(a);
        return true;
    }

    public void clearShapes()
    {
        shapes.clear();
    }

    public Bitmap getImage()
    {
        return this.getDrawingCache();
    }

    public Bitmap loadBitmapFromView()
    {
        View view=this; // lazy programming
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(a);
        else
            a.drawColor(Color.WHITE);
        view.draw(a);
        return returnedBitmap;
    }
}
