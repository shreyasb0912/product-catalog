package com.shreyasbhondve.productlist.di.component;

import com.shreyasbhondve.productlist.di.module.AdapterModule;
import com.shreyasbhondve.productlist.di.module.SearchByCategoryActivityModule;
import com.shreyasbhondve.productlist.di.scopes.ActivityScope;
import com.shreyasbhondve.productlist.ui.SearchByCategoryActivity;

import dagger.Component;

@Component(modules = {AdapterModule.class, SearchByCategoryActivityModule.class}, dependencies = ApplicationComponent.class)
@ActivityScope
public interface SearchByCategoryActivityComponent {

    void inject(SearchByCategoryActivity searchByCategoryActivity);
}
