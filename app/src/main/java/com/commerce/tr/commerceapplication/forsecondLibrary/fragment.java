package com.commerce.tr.commerceapplication.forsecondLibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commerce.tr.commerceapplication.R;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

/**
 * Created by tarfa on 9/10/17.
 */

public class fragment extends Fragment implements BarcodeRetriever{

    BarcodeCapture barcodeCapture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_new_test, container, false);
        barcodeCapture = (BarcodeCapture) getChildFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);

        return rootView;
    }


    @Override
    public void onRetrieved(Barcode barcode) {

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
