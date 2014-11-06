package com.austinoneil.Easel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by austin on 11/5/14.
 */
public class PageSelectorDialog extends DialogFragment {
    GridLayout layout;
    DialogInterface.OnDismissListener onDismissListener;

    public PageSelectorDialog(ArrayList<Bitmap> images, Context context)
    {
        super();
        layout = new GridLayout(context);
        layout.setColumnCount(4);
        for (int i = 0; i < images.size(); i++)
        {
            ImageView iv = new ImageView(context);
            iv.setMaxWidth(200);
            iv.setImageBitmap(images.get(i));
            iv.setAdjustViewBounds(true);
            iv.setPadding(10, 10, 10, 10);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            layout.addView(iv);
        }
        Log.d("PageSelectorDialog", "size of layout: " + layout.getChildCount());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        Dialog dialog = builder.create();
        dialog.setOnDismissListener(onDismissListener);
        return dialog;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener odl)
    {
        onDismissListener=odl;
    }
}
