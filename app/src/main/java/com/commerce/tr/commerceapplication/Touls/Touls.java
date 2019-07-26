package com.commerce.tr.commerceapplication.Touls;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by tarfa on 8/26/17.
 */

public class Touls {

    Context context;
    public Realm realm;

    public Touls(Context context){
        Realm.init(context);
        this.context=context;
        this.realm=Realm.getDefaultInstance();
    }


}
