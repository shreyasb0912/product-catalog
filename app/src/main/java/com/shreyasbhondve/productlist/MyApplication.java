package com.shreyasbhondve.productlist;

import android.app.Activity;
import android.app.Application;

import com.shreyasbhondve.productlist.db.DataManager;
import com.shreyasbhondve.productlist.di.component.ApplicationComponent;
import com.shreyasbhondve.productlist.di.module.ContextModule;
import com.shreyasbhondve.productlist.di.component.DaggerApplicationComponent;

import javax.inject.Inject;


public class MyApplication extends Application {

    ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this, (Application) getApplicationContext())).build();
//        applicationComponent  = DaggerApplicationComponent.builder()
//                .applicationModule(new ApplicationModule(this))
//                .build();
        applicationComponent.injectApplication(this);

    }

    public static MyApplication get(Activity activity){
        return (MyApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

