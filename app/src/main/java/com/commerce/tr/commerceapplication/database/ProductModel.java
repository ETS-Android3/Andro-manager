package com.commerce.tr.commerceapplication.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tarfa on 8/25/17.
 */

public class ProductModel extends RealmObject {
    @PrimaryKey
    private int id_product;

    private String nameProduct;
    private int priceProduct;
    private String codeBarProduct;
    private byte [] imageProduct;
    private int quantity=1;

    public ProductModel( String nameProduct, int priceProduct, String codeBarProduct, byte [] imageProduct) {

        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.codeBarProduct = codeBarProduct;
        this.imageProduct = imageProduct;

    }


    public ProductModel(){

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getCodeBarProduct() {
        return codeBarProduct;
    }

    public void setCodeBarProduct(String codeBarProduct) {
        this.codeBarProduct = codeBarProduct;
    }

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte [] imageProduct) {
        this.imageProduct = imageProduct;
    }
}
