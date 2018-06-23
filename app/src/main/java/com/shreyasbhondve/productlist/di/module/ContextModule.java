package com.shreyasbhondve.productlist.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.di.qualifier.DatabaseInfo;
import com.shreyasbhondve.productlist.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;


    private final Application mApplication;

    public ContextModule(Context context,Application app) {
        this.context = context;
        mApplication = app;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext() {
        return context;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "StockDatabase.db";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 1;
    }

    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
    }
}
