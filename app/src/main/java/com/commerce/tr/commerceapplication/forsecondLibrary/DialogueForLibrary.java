package com.commerce.tr.commerceapplication.forsecondLibrary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.commerce.tr.commerceapplication.DialogueCamera;
import com.commerce.tr.commerceapplication.R;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

/**
 * Created by tarfa on 9/10/17.
 */

public class DialogueForLibrary extends DialogFragment implements BarcodeRetriever{

    private MediaPlayer mediaPlayer;

    public interface OnFindCodeBareNew {
        public void onFind(String codeBare);

    }

    View view;
    BarcodeCapture barcodeCapture;
    OnFindCodeBareNew onFindCodeBare;

    public static int nbr=0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);



        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            view=inflater.inflate(R.layout.activity_new_test,container,false);
            barcodeCapture =(BarcodeCapture)getActivity().getSupportFragmentManager().findFragmentById(R.id.barcode);
            barcodeCapture.setRetrieval(this);

            onFindCodeBare= (OnFindCodeBareNew) getTargetFragment();


        }catch (Exception e){

        }

        return view;
    }



    public void putFragmentInsideLayout(){

        BarcodeCapture barcodeCapture=new BarcodeCapture();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();


    }

    @Override
    public void onRetrieved(Barcode barcode) {
        mediaPlayer= MediaPlayer.create(getContext(),R.raw.ouy);
        mediaPlayer.start();

        onFindCodeBare.onFind(barcode.displayValue);
        getDialog().dismiss();
        barcodeCapture.stopScanning();
    }


    @Override
    public void onRetrievedMultiple(Barcode barcode, List<BarcodeGraphic> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String s) {

        Toast.makeText(getContext(), "There is Problem !! ", Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }

    @Override
    public void onPermissionRequestDenied() {

    }

    @Override
    public void dismiss() {
        super.dismiss();
        barcodeCapture.refresh();

    }
}
