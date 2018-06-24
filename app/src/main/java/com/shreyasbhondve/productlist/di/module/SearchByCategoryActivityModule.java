package com.shreyasbhondve.productlist.di.module;

import android.content.Context;

import com.shreyasbhondve.productlist.di.qualifier.ActivityContext;
import com.shreyasbhondve.productlist.di.scopes.ActivityScope;
import com.shreyasbhondve.productlist.ui.SearchByCategoryActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchByCategoryActivityModule {
    private SearchByCategoryActivity searchByCategoryActivity;
    private Context context;

    public SearchByCategoryActivityModule(SearchByCategoryActivity searchByCategoryActivity, Context context) {
        this.searchByCategoryActivity = searchByCategoryActivity;
        this.context = context;
    }

    @Provides
    @ActivityScope
    public SearchByCategoryActivity providesSearchByCategoryActivity() {
        return searchByCategoryActivity;
    }

//    @Provides
//    @ActivityScope
//    @ActivityContext
//    public Context provideContext() {
//        return context;
//    }
}
