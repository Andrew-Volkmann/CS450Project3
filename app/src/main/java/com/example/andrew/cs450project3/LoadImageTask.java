package com.example.andrew.cs450project3;


import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.content.res.Resources;
/**
 * Created by ehar on 9/29/2016.
 */

public class LoadImageTask extends AsyncTask<Integer, Void, Bitmap> {

    ImageButton ib = null;
    int w, h, id;
    Resources res;


    public LoadImageTask(ImageButton v, Resources res) {
        ib = v;
        this.w = v.getWidth();
        this.h = v.getHeight();
        this.res = res;


    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        return Utility.decodeSampledBitmapFromResource(res, params[0], w,h);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ib.setImageBitmap(bitmap);
    }
}