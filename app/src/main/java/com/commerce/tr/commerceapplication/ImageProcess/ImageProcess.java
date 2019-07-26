package com.commerce.tr.commerceapplication.ImageProcess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by tarfa on 8/25/17.
 */

public class ImageProcess {


    /**
     *
     * @param bitmap
     */
    public static byte[] convertImageByte(Bitmap bitmap){

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        return outputStream.toByteArray();

    }

    /**
     * @param bitmap
     * @return
     */
    public static String convertImageToString(Bitmap bitmap){
        return Base64.encodeToString(convertImageByte(bitmap),Base64.DEFAULT);
    }


    public static Bitmap convertToByte(String s){
        byte[] decodedString = Base64.decode(s,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }



    public static  Bitmap getTheOriginalImage(byte [] image){

        if (image!=null){
            return  BitmapFactory.decodeByteArray(image, 0, image.length);
        }else
        {
            return null;
        }

    }




}
