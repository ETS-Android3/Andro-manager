package com.commerce.tr.commerceapplication.forsecondLibrary;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.commerce.tr.commerceapplication.R;
import com.commerce.tr.commerceapplication.home.HomeFragment;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class newTest extends AppCompatActivity implements BarcodeRetriever {
    public interface OnFindCodeBareNew {
        public void onFind(String codeBare);

    }

  public   MediaPlayer  mediaPlayer;

    OnFindCodeBareNew onFindCodeBare;
    BarcodeCapture barcodeCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        params.gravity= Gravity.CENTER;
        getWindow().setAttributes(params);





        this.setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_new_test);
         barcodeCapture=(BarcodeCapture)getSupportFragmentManager().findFragmentById(R.id.barcode);
         barcodeCapture.setShowDrawRect(true);
         barcodeCapture.shouldAutoFocus(true);
         barcodeCapture.setRetrieval(this);




        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barcodeCapture.shouldAutoFocus(true);
                barcodeCapture.refresh();
                barcodeCapture.shouldAutoFocus(true);

            }
        });



        findViewById(R.id.shape_focus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barcodeCapture.shouldAutoFocus(true);
                barcodeCapture.refresh();
                barcodeCapture.shouldAutoFocus(true);

            }
        });


        //onFindCodeBare= (OnFindCodeBareNew);

    }


    /**
     * this when the camera detect the barcode
     */
    public void playAudio(){


    }


    @Override
    public void onRetrieved(Barcode barcode) {
//        onFindCodeBare.onFind(barcode.displayValue);
        playAudio();

        barcodeCapture.stopScanning();
        Intent intent=new Intent();
        intent.putExtra("codeBar",barcode.displayValue);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onRetrievedMultiple(Barcode barcode, List<BarcodeGraphic> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String s) {


    }

    @Override
    public void onPermissionRequestDenied() {

    }
}
