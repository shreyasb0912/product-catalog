package com.shreyasbhondve.productlist.db;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private Context mContext;
    private MySqliteOpenHelper mySqliteOpenHelper;


    @Inject
    public DataManager(@ApplicationContext Context context, MySqliteOpenHelper mySqliteOpenHelper){
        Log.v("database","inside DataManager");
        this.mContext = context;
        this.mySqliteOpenHelper = mySqliteOpenHelper;
    }

    public void getProducts() throws Resources.NotFoundException,NullPointerException{
        mySqliteOpenHelper.getProducts();
    }
}
