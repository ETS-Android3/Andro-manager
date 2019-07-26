package com.commerce.tr.commerceapplication.Products;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.commerce.tr.commerceapplication.DialogueCamera;
import com.commerce.tr.commerceapplication.ImageProcess.ImageProcess;
import com.commerce.tr.commerceapplication.R;
import com.commerce.tr.commerceapplication.database.ProductModel;
import com.commerce.tr.commerceapplication.database.Utils;

import com.commerce.tr.commerceapplication.forsecondLibrary.DialogueForLibrary;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;


import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by tarfa on 8/24/17.
 */

@SuppressLint("ValidFragment")
public class AddNewItemFragment extends Fragment implements DialogueForLibrary.OnFindCodeBareNew{

    public ImageView takePicture;
    public CircleImageView imageProduct;
    public ImageView codeBareMenu;
    public TextView displayCodeBar;
    public EditText nameProduct;
    public EditText priceProduct;
    public Button addProduct;
    public Bitmap bitmap;
    public String codeBare;
    Realm realm;
    public ProgressBar progressBar;
   public int pricePro=0;

    public AddNewItemFragment(Realm realm) {

    this.realm=realm;
    }
    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add__item_product,container,false);
        takePicture= (ImageView) view.findViewById(R.id.take_image);
        imageProduct= (CircleImageView) view.findViewById(R.id.image_product);
        codeBareMenu= (ImageView) view.findViewById(R.id.codebar);
        displayCodeBar= (TextView) view.findViewById(R.id.display_codebar);
        addProduct= (Button) view.findViewById(R.id.btn_save_product);
        nameProduct= (EditText) view.findViewById(R.id.input_name_product);
        priceProduct= (EditText) view.findViewById(R.id.input_price_product);
        progressBar= (ProgressBar) view.findViewById(R.id.progrss_save_product);
        progressBar.setVisibility(View.GONE);


        addProduct.setOnClickListener(this::addOneItemOfPRoduct);
        codeBareMenu.setOnClickListener(this::opTakeBarCode);
        takePicture.setOnClickListener(this::onOpenCamera);

        NativeExpressAdView adView = (NativeExpressAdView)view.findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        return  view;

    }

    private void opTakeBarCode(View view) {
       // DialogueCamera camera=new DialogueCamera();

        Fragment fragment=getFragmentManager().findFragmentById(R.id.barcode);


        if (fragment!=null){
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
        
        DialogueForLibrary camera=new DialogueForLibrary();

        camera.show(getFragmentManager(),"");
        camera.setTargetFragment(AddNewItemFragment.this,2);
    }

    public void onOpenCamera(View view){
        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent,2);
    }

    public void setImage(Bitmap image){
        imageProduct.setImageBitmap(image);
    }


    @Override
    public void onFind(String codeBare) {

        this.codeBare=codeBare;

        displayCodeBar.post(new Runnable() {
            @Override
            public void run() {

                displayCodeBar.setText(codeBare);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public void addOneItemOfPRoduct(View view){

        String namePro=nameProduct.getText().toString();
        if (!priceProduct.getText().toString().equals("")){
          pricePro = Integer.parseInt(priceProduct.getText().toString());
        }

        byte [] image ;

        if (!namePro.equals("") ){

            progressBar.setVisibility(View.VISIBLE);
            // first get the picture and
            new AsyncTask<Void, Void, byte[]>() {
                @Override
                protected byte[] doInBackground(Void... voids) {

                    if (bitmap!=null){
                        return ImageProcess.convertImageByte(bitmap);
                    }

                    return null;

                }

                @Override
                protected void onPostExecute(byte[] aVoid) {
                    super.onPostExecute(aVoid);

                    ProductModel productModel=new ProductModel(namePro,pricePro,codeBare,aVoid);

                    Utils utils=new Utils(getContext(),realm);

                    utils.addOneProduct(productModel);

                    Toast.makeText(getContext(), "The item added", Toast.LENGTH_SHORT).show();
                    Log.e("image byte",""+aVoid);
                    progressBar.setVisibility(View.GONE);
                }
            }.execute();

        }else {
            Toast.makeText(getContext(), "Fill The blanks", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * this method for the get the bitmap for the main activity and for use it save the
     * the product item ^_^
     * @param image
     */
    public void setTheBitmapImage(Bitmap image){
        this.bitmap=image;
    }


    public ProductModel getProductByCodeBare(String codeBare){
        RealmQuery<ProductModel> query=realm.where(ProductModel.class).equalTo("codeBarProduct",codeBare);
        RealmResults<ProductModel> results=query.findAllAsync();
        if (results!=null){
            ProductModel productModel=new ProductModel(results.get(0).getNameProduct(),
                    results.get(0).getPriceProduct(),
                    results.get(0).getCodeBarProduct(),
                    results.get(0).getImageProduct());
            return productModel;
        }else {
            return null;
        }
    }

}
