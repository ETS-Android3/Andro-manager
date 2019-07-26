package com.commerce.tr.commerceapplication.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.commerce.tr.commerceapplication.Adapter.AdapterItem;
import com.commerce.tr.commerceapplication.DialogueCamera;
import com.commerce.tr.commerceapplication.MainActivity;
import com.commerce.tr.commerceapplication.R;
import com.commerce.tr.commerceapplication.database.ProductModel;
import com.commerce.tr.commerceapplication.database.Utils;
import com.commerce.tr.commerceapplication.forsecondLibrary.DialogueForLibrary;
import com.commerce.tr.commerceapplication.forsecondLibrary.newTest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * Created by tarfa on 8/24/17.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements DialogueForLibrary.OnFindCodeBareNew ,AdapterItem.OnFinishDelete,AdapterItem.OnIncreasePrice
,AdapterItem.OnDecreasePrice{



    public static int TOTALPRICE=0;

    ImageView btnCamera;
    TextView price;
    RecyclerView recyclerView;
    ArrayList<String> list;
    AdapterItem adapterItem;
    Typeface typeface;

    Utils utils;
    Realm realm;

    MainActivity activity;

    private InterstitialAd mInterstitialAd;
    private MediaPlayer mediaPlayer;

    public HomeFragment(Realm realm) {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "Mirza-Medium.ttf");

        // for the ads inters
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-6273099645029758/9891764444");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.e("TAG", "The interstitial wasn't loaded yet.");
        }

        activity = (MainActivity) getActivity();

        activity.setPriceProductToolbar("0");

        btnCamera = (ImageView) view.findViewById(R.id.btn_camera);
        //price = (TextView) view.findViewById(R.id.display_price_total);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycel_list);
        adapterItem = new AdapterItem(this,this,this,getContext(), list, typeface);
        recyclerView.setAdapter(adapterItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        utils = new Utils(getContext(), realm);


        // btn on click
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogueToGetBarCode();

            }
        });


        NativeExpressAdView adView = (NativeExpressAdView)view.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);


        return view;
    }


    /**
     * method that open the camera to scan the bar code
     */
    public void openDialogueToGetBarCode() {
        //DialogueCamera camera = new DialogueCamera();

        Fragment fragment=getFragmentManager().findFragmentById(R.id.barcode);


        if (fragment!=null){
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
         DialogueForLibrary camera=new DialogueForLibrary();
         camera.show(getFragmentManager(), "camera");
        camera.setTargetFragment(HomeFragment.this, 1);

        //startActivityForResult(new Intent(getContext(),newTest.class),10);

    }




    /**
     * method that get the string of the code bare to use it to get the price of the current
     * product
     *
     * @param codeBare
     */
    @Override
    public void onFind(final String codeBare) {

        Log.e("je suis ", "dans on find");

        if (getProductByCodeBare(codeBare) != null) {

            Log.e("mainten", "dans le si block");

            adapterItem.addItemToList(getProductByCodeBare(codeBare));

            String pricetxt=String.valueOf(getProductByCodeBare(codeBare).getPriceProduct());

            int itemPrice=getProductByCodeBare(codeBare).getPriceProduct();



            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ((MainActivity)getActivity()).setPriceProductToolbar(String.valueOf(sumProduct(itemPrice)));

                }
            });



            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapterItem.notifyDataSetChanged();
                }
            });




        } else {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "This Product not Exist", Toast.LENGTH_SHORT).show();
                }
            });


        }




    }


    public ProductModel getProductByCodeBare(String codeBare) {
        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ProductModel> query = realm.where(ProductModel.class).equalTo("codeBarProduct", codeBare);
        RealmResults<ProductModel> results = query.findAll();
        if (results.size() > 0) {
            ProductModel productModel = new ProductModel(results.get(0).getNameProduct(),
                    results.get(0).getPriceProduct(),
                    results.get(0).getCodeBarProduct(),
                    results.get(0).getImageProduct());
            return productModel;
        } else {
            return null;
        }
    }


    /**
     * this for  the add the price of the product to the list of the all
     * product list ^_^ )--ç
     * @param itemPrice
     */

    public int sumProduct(int itemPrice){
        TOTALPRICE=TOTALPRICE+itemPrice;
        return TOTALPRICE;
    }


    @Override
    public void onDelete(int priceDeleted) {
       TOTALPRICE=TOTALPRICE-priceDeleted;

        ((MainActivity)getActivity()).setPriceProductToolbar(String.valueOf(TOTALPRICE));

    }

    @Override
    public void onIncrease(int value,int originalPrice) {

        TOTALPRICE=TOTALPRICE+originalPrice;



                ((MainActivity)getActivity()).setPriceProductToolbar(String.valueOf(TOTALPRICE));

    }

    @Override
    public void onDecrease(int value) {

        TOTALPRICE=TOTALPRICE-value;


                ((MainActivity)getActivity()).setPriceProductToolbar(String.valueOf(TOTALPRICE));



    }


    /**
     * this for delete the all product from the list;
     */
    public void deleteAllproduct(){
         adapterItem.clearList();
         TOTALPRICE=0;

                 ((MainActivity)getActivity()).setPriceProductToolbar("0");

         adapterItem.notifyDataSetChanged();
    }
}
