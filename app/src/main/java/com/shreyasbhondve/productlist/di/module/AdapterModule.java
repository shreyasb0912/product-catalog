package com.shreyasbhondve.productlist.di.module;


import com.shreyasbhondve.productlist.adapter.CategoryListAdapter;
import com.shreyasbhondve.productlist.adapter.RecyclerViewAdapter;
import com.shreyasbhondve.productlist.di.scopes.ActivityScope;
import com.shreyasbhondve.productlist.ui.MainActivity;
import com.shreyasbhondve.productlist.ui.SearchByCategoryActivity;


import dagger.Module;
import dagger.Provides;


@Module(includes = {MainActivityContextModule.class,SearchByCategoryActivityModule.class})
public class AdapterModule {

    @Provides
    @ActivityScope
    public RecyclerViewAdapter getProductList(RecyclerViewAdapter.ClickListener clickListener) {
        return new RecyclerViewAdapter(clickListener);
    }

    @Provides
    @ActivityScope
    public RecyclerViewAdapter.ClickListener getClickListener(MainActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    public CategoryListAdapter getCategoryList(CategoryListAdapter.ClickListener clickListener) {
        return new CategoryListAdapter(clickListener);
    }

    @Provides
    @ActivityScope
    public CategoryListAdapter.ClickListener getCategoryListClickListener(SearchByCategoryActivity searchByCategoryActivity) {
        return searchByCategoryActivity;
    }
}
