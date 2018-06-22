package com.shreyasbhondve.productlist.di.component;

import com.shreyasbhondve.productlist.di.module.AdapterModule;
import com.shreyasbhondve.productlist.di.module.MainActivityContextModule;
import com.shreyasbhondve.productlist.di.scopes.ActivityScope;
import com.shreyasbhondve.productlist.ui.MainActivity;

import dagger.Component;


@ActivityScope
@Component(modules = {AdapterModule.class, MainActivityContextModule.class}, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

//    @ActivityContext
//    Context getContext();


    void injectMainActivity(MainActivity mainActivity);
}
