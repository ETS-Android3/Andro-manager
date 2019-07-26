package com.commerce.tr.commerceapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.commerce.tr.commerceapplication.ImageProcess.ImageProcess;
import com.commerce.tr.commerceapplication.R;
import com.commerce.tr.commerceapplication.database.ProductModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tarfa on 8/22/17.
 */

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.HolderItem> {

    /**
     * interface for the delete price
     */
    public interface OnFinishDelete{

        public void onDelete(int priceDeleted);
    }

    /**
     * for add the other value for the price total
     */
    public interface OnIncreasePrice{
        public void onIncrease(int value,int originalPrice);
    }

    /**
     *
     */

    public interface OnDecreasePrice{
        public void onDecrease(int value);
    }



    public Context context;
    public LayoutInflater inflater;
    ArrayList<String> strings;
    Typeface typeface;
    public Bitmap bitmap;
    ArrayList<ProductModel> list;
    public static int QUANTITY=0;


    // all the interface
    OnFinishDelete onFinishDelete;
    OnIncreasePrice onIncreasePrice;
    OnDecreasePrice onDecreasePrice;

    public AdapterItem(OnFinishDelete lisner,OnDecreasePrice li1,OnIncreasePrice l2,Context context, ArrayList<String> list, Typeface typeface) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.strings = list;
        this.typeface = typeface;
        this.list = new ArrayList<>();

        onFinishDelete=lisner;
        onIncreasePrice=l2;
        onDecreasePrice=li1;


    }


    public AdapterItem(Context context, ArrayList<String> list, Bitmap bitmap) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.strings = list;
        this.bitmap = bitmap;

    }


    /**
     * this for delete the all item from the list of product
     */
    public void clearList(){
        this.list.clear();
        notifyDataSetChanged();
    }

    public void addItemToList(ProductModel item) {
        this.list.add(item);

    }

    public int getSizeOf() {
        return list.size();
    }


    @Override
    public HolderItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_item_product, viewGroup, false);

        return new HolderItem(view);
    }

    @Override
    public void onBindViewHolder(HolderItem holderItem, int i) {
        holderItem.name_product.setTypeface(typeface);
        holderItem.circleImageView.setImageBitmap(ImageProcess.getTheOriginalImage(list.get(i).getImageProduct()));
        holderItem.name_product.setText(list.get(i).getNameProduct());
        holderItem.price_product_list.setText(String.valueOf(list.get(i).getPriceProduct()));
        holderItem.display_qu.setText(String.valueOf(list.get(i).getQuantity()));
        holderItem.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteProductItem(i,list.get(i).getPriceProduct());
            }
        });

        holderItem.increase_qu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPrice=list.get(i).getPriceProduct();

                // get the quantity of the current position

                // this for change the quantity of the item product

                list.get(i).setQuantity(list.get(i).getQuantity()+1);

                holderItem.display_qu.setText(String.valueOf(list.get(i).getQuantity()));

               onIncreasePrice.onIncrease(currentPrice*list.get(i).getQuantity(),currentPrice);
            }
        });

        holderItem.decrease_qu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentPrice=list.get(i).getPriceProduct();
                // change the Display of quantity
                if (list.get(i).getQuantity() > 1){

                    list.get(i).setQuantity(list.get(i).getQuantity()-1);

                    holderItem.display_qu.setText(String.valueOf(list.get(i).getQuantity()));

                    onDecreasePrice.onDecrease(currentPrice);

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class HolderItem extends RecyclerView.ViewHolder {

        TextView name_product;
        CircleImageView circleImageView;
        TextView price_product_list;
        ImageView deleteItem;
        TextView display_qu;
        ImageView increase_qu;
        ImageView decrease_qu;

        public HolderItem(View itemView) {
            super(itemView);

            name_product = (TextView) itemView.findViewById(R.id.name_product);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            price_product_list= (TextView) itemView.findViewById(R.id.price_product_list);
            deleteItem= (ImageView) itemView.findViewById(R.id.delete_product_btn);

            display_qu= (TextView) itemView.findViewById(R.id.display_qu);
            increase_qu= (ImageView) itemView.findViewById(R.id.increase_qu);
            decrease_qu= (ImageView) itemView.findViewById(R.id.decrease_qu);

        }
    }


    /**
     * method that add the same product to the list of the product
     *
     * @param position
     */
    public void increaseQuantity(int position) {

    }

    /**
     * the method - the product form list of products
     *
     * @param position
     */

    public void decryseQuantity(int position) {


    }


    /**
     * delete  the item form the list of the products
     * and change the price total
     *
     * @param position
     */
    public void deleteProductItem(int position,int price) {

        // for delete  the current item

        onFinishDelete.onDelete(price*list.get(position).getQuantity());
        list.remove(position);
        notifyDataSetChanged();

    }




}
