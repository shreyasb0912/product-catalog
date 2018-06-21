package com.shreyasbhondve.productlist.db;


import android.content.Context;

import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private Context mContext;
    private MySqliteOpenHelper mySqliteOpenHelper;


    @Inject
    public DataManager(@ApplicationContext Context context, MySqliteOpenHelper mySqliteOpenHelper){
        this.mContext = context;
        this.mySqliteOpenHelper = mySqliteOpenHelper;
    }
}
