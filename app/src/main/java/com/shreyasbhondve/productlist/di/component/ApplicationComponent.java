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

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public APIInterface getApiInterface();

    DataManager getDataManager();

    MySqliteOpenHelper getMySqliteOpenHelper();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);


}
