package com.commerce.tr.commerceapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

public class MainPage extends AppCompatActivity {



    Button scanbtn;
    TextView result;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // permission for the usage of the camera
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        Button button= (Button) findViewById(R.id.button);
        result= (TextView) findViewById(R.id.txtContent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplication(), CameraScan.class);
                startActivityForResult(intent, REQUEST_CODE);


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                      result.setText(barcode.displayValue);

                     }
                });
            }
        }
    }
}
