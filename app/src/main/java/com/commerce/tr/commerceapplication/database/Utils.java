package com.commerce.tr.commerceapplication.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by tarfa on 8/25/17.
 */

public class Utils {

    public Context context;

    public Realm realm;

    public Utils(Context context,Realm realm) {
        this.context = context;
        this.realm=realm;
    }

    public Utils(Context context) {
        this.context = context;
    }

    /**
     * this method that add one item to our database Realm
     * @param model
     */
    public void addOneProduct(ProductModel model){

        realm.beginTransaction();
        ProductModel productModel=realm.createObject(ProductModel.class,getNextKey());


        productModel.setNameProduct(model.getNameProduct());
        productModel.setImageProduct(model.getImageProduct());
        productModel.setPriceProduct(model.getPriceProduct());
        productModel.setCodeBarProduct(model.getCodeBarProduct());

        realm.commitTransaction();

        Log.e("ass","row added to database");
    }


    /**
     * get the next id
     * @return
     */

    public int getNextKey() {
        try {
            Number number = realm.where(ProductModel.class).max("id_product");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }


    /**
     * get the all product
     * @return
     */
    public List<Object> getAllProduct(){

        List<Object> list =new ArrayList<>();

        RealmQuery<ProductModel> query=realm.where(ProductModel.class);
        RealmResults<ProductModel> results=query.findAll();

        for (int i=0;i<results.size();i++){

           String nameProduct=results.get(i).getNameProduct();
           int priceProduct=results.get(i).getPriceProduct();
           String codeBare=results.get(i).getCodeBarProduct();
           byte [] imageProduct=results.get(i).getImageProduct();
           ProductModel model=new ProductModel(nameProduct,priceProduct,codeBare,imageProduct);
           list.add(model);

        }

        return list;
    }



}
