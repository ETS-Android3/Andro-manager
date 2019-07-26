package com.commerce.tr.commerceapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.commerce.tr.commerceapplication.Dialogue.DialogueUpdateProduct;
import com.commerce.tr.commerceapplication.ImageProcess.ConvertImageFromByteToBitmap;
import com.commerce.tr.commerceapplication.ImageProcess.ImageProcess;
import com.commerce.tr.commerceapplication.MainActivity;
import com.commerce.tr.commerceapplication.Products.FragmentListProduct;
import com.commerce.tr.commerceapplication.R;
import com.commerce.tr.commerceapplication.database.ProductModel;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by tarfa on 8/25/17.
 */

public class AdapterListItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    public LayoutInflater inflater;
    public ArrayList<String> strings;
    public Typeface typeface;
    public Bitmap bitmap;
      List<Object> listItem;
     FragmentManager fragmentManager;

     FragmentListProduct fragmentListProduct;

     ArrayList<NativeExpressAdView> mAdsList;

     public static final int ITEM_PRODUCT=0;
     public static final int ITEM_ADS_NATIVE=1;



    public AdapterListItem(Context context, ArrayList<String> list, Typeface typeface) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.strings = list;
        this.typeface = typeface;

    }


    public AdapterListItem(Context context, byte[] image, Bitmap bitmap) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bitmap = bitmap;
    }

    public AdapterListItem(Context context, List<Object> allProduct, FragmentManager fragmentManager, FragmentListProduct fragmentListProduct) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listItem = allProduct;
        this.fragmentManager=fragmentManager;
        this.fragmentListProduct=fragmentListProduct;

    }


    public void addItemToList(String item) {
        this.strings.add(item);

    }

    public int getSizeOf() {
        return strings.size();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int typeView) {


        View view = inflater.inflate(R.layout.item_list_of_product, viewGroup, false);
        return new HolderItemList(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType=getItemViewType(position);
        switch (viewType){
/*
            case ITEM_ADS_NATIVE:

                AdsViewHolder adsViewHolder= (AdsViewHolder) holder;

                if (listItem.get(position) instanceof NativeExpressAdView){
                    AdsViewHolder nativeExpressHolder =
                            (AdsViewHolder) holder;
                    NativeExpressAdView adView =
                            (NativeExpressAdView) listItem.get(position);
                    ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                    // The NativeExpressAdViewHolder recycled by the RecyclerView may be a different
                    // instance than the one used previously for this position. Clear the
                    // NativeExpressAdViewHolder of any subviews in case it has a different
                    // AdView associated with it, and make sure the AdView for this position doesn't
                    // already have a parent of a different recycled NativeExpressAdViewHolder.
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }

                    // Add the Native Express ad to the native express ad view.
                    adCardView.addView(adView);
                }
*/
            case ITEM_PRODUCT:
                default:

                    if(holder instanceof HolderItemList){
                        HolderItemList holderItem= (HolderItemList) holder;
                        holderItem.name_product.setTypeface(typeface);
                        initializationMenu(holderItem, position);
                        holderItem.circleImageView.setImageBitmap(ImageProcess.getTheOriginalImage(((ProductModel)listItem.get(position)).getImageProduct()));
                        holderItem.name_product.setText(((ProductModel)listItem.get(position)).getNameProduct());
                        holderItem.price_product.setText(Integer.toString(((ProductModel)listItem.get(position)).getPriceProduct()));

                    }

        }

    }



    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position % 4 ==0 ) ? ITEM_ADS_NATIVE : ITEM_PRODUCT;
    }

    public class HolderItemList extends RecyclerView.ViewHolder {

        TextView name_product, price_product;
        CircleImageView circleImageView;
        Button menu;

        public HolderItemList(View itemView) {
            super(itemView);

            name_product = (TextView) itemView.findViewById(R.id.name_product);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            price_product = (TextView) itemView.findViewById(R.id.priceTxt);
            menu = (Button) itemView.findViewById(R.id.menuProduct);


        }
    }


    /**
     * this the view holder of the ads
     */
    public class AdsViewHolder extends RecyclerView.ViewHolder{

        public AdsViewHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * this method of the initialize of the menu of the list of the product
     * @param item
     * @param position
     */
    public void initializationMenu(final HolderItemList item, int position) {
        item.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, item.menu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_product_option, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getTitle().equals("Modify")) {

                                updateProduct((ProductModel)listItem.get(position));

                        } else {
                            if (menuItem.getTitle().equals("Delete")) {

                                deleteProduct(position);
                            }
                        }
                        return true;
                    }

                });

                popupMenu.show();
            }
        });


    }


    /**
     * Method for update the current product
     * @param model
     */
    public void updateProduct(ProductModel model) {
        DialogueUpdateProduct dialogueUpdateProduct=new DialogueUpdateProduct(model);
        dialogueUpdateProduct.show(fragmentManager,"fragment_one");
        dialogueUpdateProduct.setTargetFragment(fragmentListProduct,101);
    }
     /**
     * for delete the item of product from The Database
     */
    public void deleteProduct(int position) {

        // delete from the database
        deleteFromDataBase(position);
        // delete from the list
        listItem.remove(listItem.get(position));
        // notify that changes
        notifyDataSetChanged();
    }


    /**
     * delete one item form the list of the product
     * @param id
     */
    public void deleteFromDataBase(int id){
        Realm.init(context);
        Realm realm=Realm.getDefaultInstance();

        RealmResults<ProductModel> results=realm.where(ProductModel.class).equalTo("id_product",id).findAll();

       realm.executeTransaction(new Realm.Transaction() {
           @Override
           public void execute(Realm realm) {

               if (results.size()==1){

                   ProductModel model=results.get(0);
                   model.deleteFromRealm();
               }


           }
       });

    }



}
