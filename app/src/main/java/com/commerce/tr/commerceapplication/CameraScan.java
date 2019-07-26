package com.commerce.tr.commerceapplication;

import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CameraScan extends AppCompatActivity {
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera);
        cameraView = (SurfaceView) findViewById(R.id.cameraImage);
        cameraView.setZOrderMediaOverlay(true);

        holder = cameraView.getHolder();

        barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.EAN_13|Barcode.EAN_8|Barcode.CONTACT_INFO|Barcode.CODABAR|Barcode.CODE_128|Barcode.CODE_93|Barcode.CODE_39|Barcode.PRODUCT|Barcode.ITF|Barcode.ISBN |Barcode.DATA_MATRIX |Barcode.QR_CODE|Barcode.UPC_A|Barcode.UPC_E )
                .build();


        if(!barcode.isOperational()){
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }
        cameraSource = new CameraSource.Builder(getApplication(), barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setRequestedPreviewSize(1920,1024)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(CameraScan.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(cameraView.getHolder());
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }


        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            final SparseArray<Barcode> barcodes =  detections.getDetectedItems();
            if(barcodes.size() > 0){
                Intent intent = new Intent();
                intent.putExtra("barcode", barcodes.valueAt(0));
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    });
}
}
