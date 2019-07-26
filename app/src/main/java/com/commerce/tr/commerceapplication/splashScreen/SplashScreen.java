package com.commerce.tr.commerceapplication.splashScreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.commerce.tr.commerceapplication.MainActivity;
import com.commerce.tr.commerceapplication.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
               startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, 3000);
    }
}
