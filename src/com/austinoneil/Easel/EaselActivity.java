package com.austinoneil.Easel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class EaselActivity extends Activity implements TextureView.SurfaceTextureListener {
    DrawingBoardView db;
    Paint p;
    ColorPickerDialog dialog;
    String filename="";
    Bitmap bmp;
    ArrayList<Bitmap> bitmaps=new ArrayList<Bitmap>();
    int currentPage=0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getActionBar().hide();
        db=(DrawingBoardView)this.findViewById(R.id.drawing_board);
    }



    public void saveImage(View view)
    {
        bmp=db.loadBitmapFromView();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Image");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filename = input.getText().toString() + ".png";
                try
                {
                    String path = Environment.getExternalStorageDirectory().toString();
                    OutputStream fOut = null;
                    File file = new File(path, filename);
                    file.createNewFile();

                    fOut = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, fOut);
                    fOut.flush();
                    fOut.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                }
                catch (Exception e)
                {
                    Log.d("saveImage", "something went wrong with saving");
                    e.printStackTrace();
                }
                finally
                {
                    filename="";
                }
            }
        });
        builder.show();
    }

    public void setColor(View view)
    {
        db.p.setStrokeWidth(10);
        ImageView currentBrushView = (ImageView)findViewById(R.id.current_view);
        switch(view.getId())
        {
            case R.id.set_red:
                db.setColor(Color.RED);
                currentBrushView.setImageDrawable(new ColorDrawable(Color.RED));
                break;
            case R.id.set_orange:
                db.setColor(Color.rgb(0xff, 0xaf, 0x00));
                currentBrushView.setImageDrawable(new ColorDrawable(Color.rgb(0xff, 0xaf, 0x00)));
                break;
            case R.id.set_yellow:
                db.setColor(Color.rgb(0xff, 0xff, 0x00));
                currentBrushView.setImageDrawable(new ColorDrawable(Color.rgb(0xff, 0xff, 0x00)));
                break;
            case R.id.set_green:
                db.setColor(Color.rgb(0x00, 0xff, 0x00));
                currentBrushView.setImageDrawable(new ColorDrawable(Color.rgb(0x00, 0xff, 0x00)));
                break;
            case R.id.set_blue:
                db.setColor(Color.rgb(0x00, 0x00, 0xff));
                currentBrushView.setImageDrawable(new ColorDrawable(Color.rgb(0x00, 0x00, 0xff)));
                break;
            case R.id.set_purple:
                db.setColor(Color.rgb(0xff, 0x00, 0xff));
                currentBrushView.setImageDrawable(new ColorDrawable(Color.rgb(0xff, 0x00, 0xff)));
                break;
            case R.id.set_brown:
                db.setColor(Color.rgb(0xc2, 0x54, 0x29));
                currentBrushView.setImageDrawable(new ColorDrawable(Color.rgb(0xc2, 0x54, 0x29)));
                break;
            case R.id.set_white:
                db.setColor(Color.WHITE);
                currentBrushView.setImageDrawable(new ColorDrawable(Color.WHITE));
                break;
            case R.id.erase:
                db.setColor(Color.BLACK);
                db.p.setStrokeWidth(30);
                currentBrushView.setImageDrawable(getResources().getDrawable(R.drawable.eraser));
                break;
        }
        dialog.dismiss();
    }

    public void nextPage(View view)
    {
        ImageButton back=(ImageButton)findViewById(R.id.previous_page);
        back.setEnabled(true);
        ToggleButton tb=(ToggleButton)view;
        currentPage++;
        Log.d("currentPage", currentPage+"");
        if(currentPage>=bitmaps.size())
        {
            bitmaps.add(db.loadBitmapFromView().copy(Bitmap.Config.ARGB_8888, true));
            db.clearBitmap();
            tb.setChecked(true);
        }
        else
        {
            bitmaps.set(currentPage-1, db.loadBitmapFromView());
            db.setCurrentBitmap(bitmaps.get(currentPage));

            tb.setChecked(false);
        }

    }

    public void previousPage(View view)
    {
        if(currentPage!=bitmaps.size())
        {
            bitmaps.set(currentPage, db.loadBitmapFromView().copy(Bitmap.Config.ARGB_8888, true));
        }
        else
        {
            bitmaps.add(db.loadBitmapFromView().copy(Bitmap.Config.ARGB_8888, true));
        }
        currentPage--;
        Log.d("currentPage", currentPage+"");
        ToggleButton tb=(ToggleButton)findViewById(R.id.next_page);
        tb.setChecked(false);
        if(currentPage==0)
        {
            view.setEnabled(false);
        }
        //db.clearBitmap();
        db.setCurrentBitmap(bitmaps.get(currentPage).copy(Bitmap.Config.ARGB_8888, true));
    }

    public void setColorDialog(View view)
    {
        dialog=new ColorPickerDialog();
        dialog.show(getFragmentManager(), "");

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