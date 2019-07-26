package com.commerce.tr.commerceapplication.Products;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commerce.tr.commerceapplication.Adapter.AdapterListItem;
import com.commerce.tr.commerceapplication.R;
import com.commerce.tr.commerceapplication.database.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by tarfa on 8/25/17.
 */


@SuppressLint("ValidFragment")
public class FragmentListProduct extends Fragment  {

    RecyclerView recyclerView;
    ArrayList<String> list = new ArrayList<>();
    Realm realm;
    FragmentManager fragmentManager;
    FragmentListProduct fragmentListProduct;
    private AdView mAdView;
    public List<Object> mRecycleViewList;

    public FragmentListProduct(){

    }


    public FragmentListProduct(Realm realm, FragmentManager supportFragmentManager) {

        this.realm = realm;
        this.fragmentManager=supportFragmentManager;
        fragmentListProduct=this;


    }

    public void initializeAds(View view){

        NativeExpressAdView adView = (NativeExpressAdView)view. findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

    }
      InterstitialAd mInterstitialAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecycleViewList=new ArrayList<>();
        mRecycleViewList=getAllProduct();

        View view = inflater.inflate(R.layout.fragment_all_product, container, false);
        initializeAds(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_all_item);
        // here get the all product
        AdapterListItem adapterItem = new AdapterListItem(getContext(),mRecycleViewList,fragmentManager,fragmentListProduct);
        recyclerView.setAdapter(adapterItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // for the ads inters
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.e("TAG", "The interstitial wasn't loaded yet.");
        }


        return view;
    }


    /**
     * get the all product to display into the listView ^_^
     *
     * @return
     */
    public List<Object> getAllProduct() {

        Utils utils = new Utils(getContext(), realm);
        return utils.getAllProduct();

    }


}
