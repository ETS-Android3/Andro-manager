package com.commerce.tr.commerceapplication.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.commerce.tr.commerceapplication.ImageProcess.ImageProcess;
import com.commerce.tr.commerceapplication.R;

public class test extends AppCompatActivity {
    Button take,convert;
    ImageView imageView;
    public byte[] imagebyte;


    public Bitmap bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        take=(Button) findViewById(R.id.take);
        convert= (Button) findViewById(R.id.origina);
        imageView= (ImageView) findViewById(R.id.imageView2);

        take.setOnClickListener(this::openCamera);
        convert.setOnClickListener(this::ConvertOriginal);
    }

    private void ConvertOriginal(View view) {

        imageView.setImageBitmap(ImageProcess.getTheOriginalImage(imagebyte));

    }

    private void openCamera(View view) {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){

            Bundle bundle=data.getExtras();
            Bitmap bitmap= (Bitmap)bundle.get("data");
            imagebyte= ImageProcess.convertImageByte(bitmap);

        }
    }
}
