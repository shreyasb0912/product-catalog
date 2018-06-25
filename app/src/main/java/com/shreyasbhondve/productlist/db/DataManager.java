package com.shreyasbhondve.productlist.db;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;
import com.shreyasbhondve.productlist.pojo.Ranking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private Context mContext;
    private MySqliteOpenHelper mySqliteOpenHelper;
    private SharedPreferenceHelper mSharedPreferenceHelper;


    @Inject
    public DataManager(@ApplicationContext Context context, MySqliteOpenHelper mySqliteOpenHelper,SharedPreferenceHelper sharedPreferenceHelper){
        Log.v("database","inside DataManager");
        this.mContext = context;
        this.mySqliteOpenHelper = mySqliteOpenHelper;
        this.mSharedPreferenceHelper = sharedPreferenceHelper;
    }

    public void saveAccessToken(String accessToken) {
        mSharedPreferenceHelper.put(SharedPreferenceHelper.PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    public String getAccessToken(){
        return mSharedPreferenceHelper.get(SharedPreferenceHelper.PREF_KEY_ACCESS_TOKEN, null);
    }

    public void setFirstRun(String key, boolean value) {
        mSharedPreferenceHelper.setFirstRun(key,value);
    }

    public boolean getFirstRun(String key, boolean defaultValue) {
        return mSharedPreferenceHelper.getFirstRun(key,defaultValue);
    }

    public boolean getVariants() throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.isVariantsPresent();
    }

    public List<ProductCatalog.Category.Product> getProducts() throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.getProducts();
    }

    public List<ProductCatalog.Category.Product.Variants> getVariants(String prod_id) throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.getVariants(prod_id);
    }

    public List<ProductCatalog.Category.Product> sortProducts(String filter) throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.sortProducts(filter);
    }

    public List<ProductCatalog.Category.Product> searchProducts(String keyword) throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.searchProducts(keyword);
    }

    public List<ProductCatalog.Category> getCategories() throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.getCategories();
    }

    public List<ProductCatalog.Category.Product> getCategorizedProducts(String cat_id) throws Resources.NotFoundException,NullPointerException{
        return mySqliteOpenHelper.getCategorizedProducts(cat_id);
    }

    public void insertCategory(ProductCatalog.Category category){
        mySqliteOpenHelper.insertCategory(category);
    }

    public void insertProduct(ProductCatalog.Category.Product product){
        mySqliteOpenHelper.insertProduct(product);
    }

    public void insertVariant(ProductCatalog.Category.Product.Variants variants){
        mySqliteOpenHelper.insertVariant(variants);
    }

    public void insertViewRankings(Ranking.ProductRanking.Products ranking){
        mySqliteOpenHelper.insertViewRanking(ranking);
    }

    public void insertOrderRankings(Ranking.ProductRanking.Products ranking){
        mySqliteOpenHelper.insertOrderRanking(ranking);
    }

    public void insertShareRankings(Ranking.ProductRanking.Products ranking){
        mySqliteOpenHelper.insertShareRanking(ranking);
    }

}
