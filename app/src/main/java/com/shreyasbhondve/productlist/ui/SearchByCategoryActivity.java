package com.shreyasbhondve.productlist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shreyasbhondve.productlist.MyApplication;
import com.shreyasbhondve.productlist.R;
import com.shreyasbhondve.productlist.adapter.CategoryListAdapter;
import com.shreyasbhondve.productlist.db.DataManager;
import com.shreyasbhondve.productlist.di.component.ApplicationComponent;
import com.shreyasbhondve.productlist.di.component.DaggerSearchByCategoryActivityComponent;
import com.shreyasbhondve.productlist.di.component.SearchByCategoryActivityComponent;
import com.shreyasbhondve.productlist.di.module.SearchByCategoryActivityModule;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;

import java.util.List;

import javax.inject.Inject;

public class SearchByCategoryActivity extends AppCompatActivity implements CategoryListAdapter.ClickListener {

    SearchByCategoryActivityComponent searchByCategoryActivityComponent;

    @Inject
    DataManager dataManager;

    @Inject
    public CategoryListAdapter recyclerViewAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_category);

//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle("Select Category");

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        searchByCategoryActivityComponent = DaggerSearchByCategoryActivityComponent.builder()
                .searchByCategoryActivityModule(new SearchByCategoryActivityModule(SearchByCategoryActivity.this,this))
                .applicationComponent(applicationComponent)
                .build();

        searchByCategoryActivityComponent.inject(this);

        List<ProductCatalog.Category> categoryList = dataManager.getCategories();
        recyclerViewAdapter.setData(categoryList);
        recyclerView.setAdapter(recyclerViewAdapter);


    }


    @Override
    public void launchIntent(String id) {
        Log.v("database","searching cat_id: " + id);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("cat_id",id);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
