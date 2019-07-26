package com.commerce.tr.commerceapplication;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * Created by tarfa on 8/21/17.
 */

public class DialogueCamera extends DialogFragment {

    public interface OnFindCodeBare {
        public void onFind(String codeBare);

    }

// declaration

    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder surfaceHolder;

    SurfaceView cameraView;


    OnFindCodeBare onFindCodeBare;

    public static int nbr=0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
 

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        nbr=0;

        View view = inflater.inflate(R.layout.layout_camera, container, false);
        cameraView = (SurfaceView) view.findViewById(R.id.cameraImage);
        onFindCodeBare= (OnFindCodeBare)getTargetFragment();

        cameraView.setZOrderMediaOverlay(true);

        surfaceHolder=cameraView.getHolder();

        barcode = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        if(!barcode.isOperational()){
            Toast.makeText(getContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            getDialog().dismiss();
        }


        cameraSource = new CameraSource.Builder(getActivity(), barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setRequestedPreviewSize(1920,1024)
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (cameraSource != null) {
                    cameraSource.release();
                    cameraSource = null;
                }

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
                    nbr++;

                    Log.e("the nbr", barcodes.size() +"");
                    Log.e("i detecte","one "+barcodes.valueAt(0).displayValue);
                    barcode.release();
                    getDialog().dismiss();
                    if(nbr==1){
                        onFindCodeBare.onFind(barcodes.valueAt(0).displayValue);
                    }


                }
            }
        });


        return view;
    }
}
