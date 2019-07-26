package com.commerce.tr.commerceapplication.ImageProcess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;

/**
 * Created by tarfa on 8/26/17.
 */

public class ConvertImageFromByteToBitmap extends AsyncTask<Void, Void, Bitmap> {

    public interface OnFinishConversionImage{
        public void onFinishConversion(Bitmap image);
    }

    public Context context;
    public ProgressBar progressBar;
    public byte [] bitmap;
    OnFinishConversionImage onFinishConversionImage;

    public ConvertImageFromByteToBitmap(Context context, ProgressBar progressBar, byte[] bitmap, OnFinishConversionImage image) {
        this.context = context;
        this.progressBar = progressBar;
        this.bitmap = bitmap;
        onFinishConversionImage=image;

    }

    @Override
    protected Bitmap doInBackground(Void... voids) {

        return getTheOriginalImage(bitmap);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap aVoid) {
        super.onPostExecute(aVoid);

        onFinishConversionImage.onFinishConversion(aVoid);

    }


    /**
     *
     * @param bitmap
     */
    public static byte[] convertImageByte(Bitmap bitmap){

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        return outputStream.toByteArray();

    }



    public static  Bitmap getTheOriginalImage(byte [] image){

        return  BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
