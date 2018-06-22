package com.shreyasbhondve.productlist.di.component;

import android.content.Context;

import com.shreyasbhondve.productlist.MyApplication;
import com.shreyasbhondve.productlist.db.DataManager;
import com.shreyasbhondve.productlist.db.MySqliteOpenHelper;
import com.shreyasbhondve.productlist.di.module.ContextModule;
import com.shreyasbhondve.productlist.di.module.RetrofitModule;
import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.di.scopes.ApplicationScope;
import com.shreyasbhondve.productlist.retrofit.APIInterface;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public void injectApplication(MyApplication myApplication);

    @ApplicationContext
    public Context getContext();


    public APIInterface getApiInterface();

    DataManager getDataManager();

    MySqliteOpenHelper getMySqliteOpenHelper();


}
