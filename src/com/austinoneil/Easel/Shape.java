package com.austinoneil.Easel;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by austin on 9/20/14.
 */
public class Shape
{
    float x;
    float y;
    Paint paint;
    String type;

    public Shape(String type, float x, float y, Paint paint)
    {
        this.type=type;
        this.x=x;
        this.y=y;
        this.paint=paint;
    }
}
