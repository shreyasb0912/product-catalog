package com.shreyasbhondve.productlist.db;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.pojo.Category;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private Context mContext;
    private MySqliteOpenHelper mySqliteOpenHelper;


    @Inject
    public DataManager(@ApplicationContext Context context, MySqliteOpenHelper mySqliteOpenHelper){
        Log.v("database","inside DataManager");
        this.mContext = context;
        this.mySqliteOpenHelper = mySqliteOpenHelper;
    }

    public boolean getVariants() throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.isVariantsPresent();
    }

    public boolean getProducts() throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.isProductsPresent();
    }

    public boolean getCategories() throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.isCategoriesPresent();
    }

    public void insertCategory(ProductCatalog.Category category) throws Exception{
        mySqliteOpenHelper.insertCategory(category);
    }

    public void insertProduct(ProductCatalog.Category.Product product) throws Exception{
        mySqliteOpenHelper.insertProduct(product);
    }

    public void insertVariant(ProductCatalog.Category.Product.Variants variants) throws Exception{
        mySqliteOpenHelper.insertVariant(variants);
    }
}
