package com.austinoneil.Easel;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by austin on 9/20/14.
 */
public class Circle extends Shape
{
    public float radius;

    public Circle(float x, float y,float radius, Paint paint)
    {
        super("circle",x,y,paint);
        this.radius=radius;
    }
}
