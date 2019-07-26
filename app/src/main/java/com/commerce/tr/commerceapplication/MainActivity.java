package com.commerce.tr.commerceapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.commerce.tr.commerceapplication.Adapter.AdapterItem;
import com.commerce.tr.commerceapplication.Products.AddNewItemFragment;
import com.commerce.tr.commerceapplication.Products.FragmentListProduct;
import com.commerce.tr.commerceapplication.database.ProductModel;
import com.commerce.tr.commerceapplication.forsecondLibrary.newTest;
import com.commerce.tr.commerceapplication.home.HomeFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{




    AddNewItemFragment addNewItemFragment;


    ImageView btnCamera;
    TextView price;
    RecyclerView recyclerView;
    ArrayList<String> list;
    AdapterItem adapterItem;
    Typeface typeface;

    NavigationView navigationView;
    Realm realm;

    HomeFragment homeFragment;

    public  TextView toolbarprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarprice= (TextView) findViewById(R.id.priceToolbar);

        initializeAllFragment();

        Realm.init(this);
        realm=Realm.getDefaultInstance();




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startFragment();




    }


    /**
     * this method using for set the price of the product from the other place
     */
    public void setPriceProductToolbar(String price){

        toolbarprice.post(new Runnable() {
            @Override
            public void run() {
                toolbarprice.setText(price);
            }
        });

    }



    /**
     * for initialize all the fragment of the application to use
     **/

    public void initializeAllFragment(){

        homeFragment=new HomeFragment(realm);

    }


    /**
     * this the method that indicate the first fragment to start is the home fragment
     */
    public void startFragment(){


        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,homeFragment);
        fragmentTransaction.commit();
        navigationView.getMenu().findItem(R.id.nav_camera).setChecked(true);

    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            if ( homeFragment.isVisible()) {
                homeFragment.deleteAllproduct();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            homeFragment=new HomeFragment(realm);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content,homeFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_gallery) {
            addNewItemFragment =new AddNewItemFragment(realm);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content,addNewItemFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_slideshow) {

            FragmentListProduct fragment =new FragmentListProduct(realm,getSupportFragmentManager());
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Sum Price is Application That can you use it for sell product easy ^_^ you cant find it with is name in play store Sum Product good luck friends";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_send) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Sum Price is Application That can you use it for sell product easy ^_^ you cant find it with is name in play store Sum Product good luck friends";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * method to get the code bar by the camera when the user press the btn of camera
     * and get the barcode
     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null){


        if (requestCode == 1 &&resultCode==RESULT_OK){

            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                price.post(new Runnable() {
                    @Override
                    public void run() {
                        price.setText(barcode.displayValue);

                    }
                });
            }
        }else {

            if (requestCode==2){

                        Log.e("inside ","hhhhh");
                        Bundle bundle=data.getExtras();
                        Bitmap bitmap= (Bitmap) bundle.get("data");

                        addNewItemFragment.setImage(bitmap);
                        addNewItemFragment.setTheBitmapImage(bitmap);

            }
        }


    }


    }


}
