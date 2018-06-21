package com.shreyasbhondve.productlist.di.component;

import com.shreyasbhondve.productlist.di.scopes.ActivityScope;
import com.shreyasbhondve.productlist.ui.DetailActivity;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface DetailActivityComponent {

    void inject(DetailActivity detailActivity);
}
