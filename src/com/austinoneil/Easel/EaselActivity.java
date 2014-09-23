package com.austinoneil.Easel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EaselActivity extends Activity implements TextureView.SurfaceTextureListener {
    DrawingBoard db;
    Paint p;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getActionBar().hide();
        db=(DrawingBoard)this.findViewById(R.id.drawing_board);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        db.pause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        db.resume();
    }

    public void saveImage(View view)
    {
        Bitmap bmp=db.loadBitmapFromView();

        Toast toast=new Toast(this);
        ImageView bmpView = new ImageView(this);
        bmpView.setImageBitmap(bmp);
        toast.setView(bmpView);
        toast.show();
        String filename="out.png";

        // File stuff
        try
        {
            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            File file = new File(path, "out.png");
            file.createNewFile();

            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setColor(View view)
    {
        db.clearShapes(); // This is necessary to prevent the previous polyline of shapes from having its color changed.
        db.diameter=10;
        switch(view.getId())
        {
            case R.id.set_red:
                db.setColor(Color.RED);
                break;
            case R.id.set_orange:
                db.setColor(Color.rgb(0xff, 0xaf, 0x00));
                break;
            case R.id.set_yellow:
                db.setColor(Color.rgb(0xff, 0xff, 0x00));
                break;
            case R.id.set_green:
                db.setColor(Color.rgb(0x00, 0xff, 0x00));
                break;
            case R.id.set_blue:
                db.setColor(Color.rgb(0x00, 0x00, 0xff));
                break;
            case R.id.set_purple:
                db.setColor(Color.rgb(0xff, 0x00, 0xff));
                break;
            case R.id.set_brown:
                db.setColor(Color.rgb(0xc2, 0x54, 0x29));
                break;
            case R.id.erase:
                db.setColor(Color.BLACK);
                db.diameter=30;
                db.p.setStrokeWidth(30);
                break;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
