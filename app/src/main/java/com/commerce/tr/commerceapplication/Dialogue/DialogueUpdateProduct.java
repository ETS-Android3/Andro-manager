package com.commerce.tr.commerceapplication.Dialogue;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.commerce.tr.commerceapplication.ImageProcess.ImageProcess;
import com.commerce.tr.commerceapplication.R;
import com.commerce.tr.commerceapplication.database.ProductModel;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by tarfa on 8/27/17.
 */

@SuppressLint("ValidFragment")
public class DialogueUpdateProduct extends DialogFragment {

    EditText nameProduct;
    EditText priceProduct;
    TextView codeBaretext;
    CircleImageView circleImageView;
    TextView cansel;
    TextView saveChangeBtn;


    ProductModel model;

    @SuppressLint("ValidFragment")
    public DialogueUpdateProduct(ProductModel model) {

        this.model = model;

    }

    public interface OnUpdateProduct {
        public void onUpdate(boolean b);
    }

    OnUpdateProduct updateProduct;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialogue_update_product, container, false);


        nameProduct = (EditText) view.findViewById(R.id.input_name_product);
        priceProduct = (EditText) view.findViewById(R.id.input_price_product);
        codeBaretext = (TextView) view.findViewById(R.id.display_codebar);
        circleImageView = (CircleImageView) view.findViewById(R.id.image_product);
        cansel = (TextView) view.findViewById(R.id.cancel);
        saveChangeBtn = (TextView) view.findViewById(R.id.update_product_btn);

        saveChangeBtn.setOnClickListener(this::saveChange);
        cansel.setOnClickListener(this::cancelDialogue);


        initializeWidget();

        return view;
    }

    private void saveChange(View view) {

        String name = nameProduct.getText().toString();
        String price = priceProduct.getText().toString();
        String code = codeBaretext.getText().toString();

        if (name.equals("") | price.equals("")) {
            Toast.makeText(getContext(), "Check your fields", Toast.LENGTH_SHORT).show();
        } else {

            ProductModel model2 = new ProductModel();
            model2.setId_product(model.getId_product());
            model2.setNameProduct(name);
            model2.setPriceProduct(Integer.valueOf(price));
            model2.setCodeBarProduct(code);
            model2.setImageProduct(model.getImageProduct());

            Realm.init(getContext());
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            ProductModel results = realm.where(ProductModel.class).equalTo("codeBarProduct", code).findFirst();

            results.setPriceProduct(Integer.valueOf(price));
            results.setNameProduct(name);

            realm.insertOrUpdate(results);
            realm.commitTransaction();

         //updateProduct.onUpdate(true);

            getDialog().dismiss();

        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{

            updateProduct= (OnUpdateProduct)context;

        }catch (Exception e){

        }


    }

    private void cancelDialogue(View view) {

        getDialog().dismiss();

    }



    public void initializeWidget() {

        nameProduct.setText(model.getNameProduct());
        priceProduct.setText(Integer.toString(model.getPriceProduct()));
        codeBaretext.setText(model.getCodeBarProduct());
        circleImageView.setImageBitmap(ImageProcess.getTheOriginalImage(model.getImageProduct()));


    }
}
