package com.shreyasbhondve.productlist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.shreyasbhondve.productlist.MyApplication;
import com.shreyasbhondve.productlist.R;
import com.shreyasbhondve.productlist.adapter.RecyclerViewAdapter;
import com.shreyasbhondve.productlist.db.DataManager;
import com.shreyasbhondve.productlist.di.component.ApplicationComponent;
import com.shreyasbhondve.productlist.di.component.DaggerMainActivityComponent;
import com.shreyasbhondve.productlist.di.component.MainActivityComponent;
import com.shreyasbhondve.productlist.di.module.MainActivityContextModule;
import com.shreyasbhondve.productlist.di.qualifier.ActivityContext;
import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;
import com.shreyasbhondve.productlist.pojo.Ranking;
import com.shreyasbhondve.productlist.retrofit.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Callback;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ClickListener {

    private RecyclerView recyclerView;
    MainActivityComponent mainActivityComponent;

    @Inject
    public RecyclerViewAdapter recyclerViewAdapter;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    @Inject
    @ActivityContext
    public Context activityContext;

    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectMainActivity(this);
        recyclerView.setAdapter(recyclerViewAdapter);


        boolean firstRun = dataManager.getFirstRun("first_run", true);
        Log.v("database", "IS first run? " + firstRun);
        if (firstRun) {
            apiInterface.getCategories("https://stark-spire-93433.herokuapp.com/json/").enqueue(new Callback<ProductCatalog>() {
                @Override
                public void onResponse(retrofit2.Call<ProductCatalog> call, retrofit2.Response<ProductCatalog> response) {

                    List<ProductCatalog.Category> categoryList = response.body().categories;
                    new ParseData().execute(categoryList);

                }

                @Override
                public void onFailure(retrofit2.Call<ProductCatalog> call, Throwable t) {

                }
            });
        } else {
            dataManager.getCategories();
        }


    }

    public class ParseData extends AsyncTask<List<ProductCatalog.Category>, Void, Void> {

        ArrayList<ProductCatalog.Category> newCategoryList = new ArrayList<>();
        ArrayList<ProductCatalog.Category.Product> newProductList = new ArrayList<>();
        ArrayList<ProductCatalog.Category.Product.Variants> newVariantsList = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(List<ProductCatalog.Category>... categoryLists) {

            List<ProductCatalog.Category> categoryList = categoryLists[0];
            //Log.v("database", "\n----------From server--------\n");
            for (int i = 0; i < categoryList.size(); i++) {
                ProductCatalog.Category newCategory = new ProductCatalog.Category();
                ProductCatalog.Category category = categoryList.get(i);
                String cat_id = category.getId();
                newCategory.setId(cat_id);
                newCategory.setName(category.getName());
                //Log.v("database", "\ncat_id: " + cat_id + "\ncat_name: " + category.getName());
                List<ProductCatalog.Category.Product> productList = category.getProducts();
                for (int j = 0; j < productList.size(); j++) {
                    ProductCatalog.Category.Product newProduct = new ProductCatalog.Category.Product();
                    ProductCatalog.Category.Product product = productList.get(j);
                    String prod_id = product.getId();
                    newProduct.setCat_id(cat_id);
                    newProduct.setId(prod_id);
                    newProduct.setName(product.getName());
                    newProduct.setDate_added(product.getDate_added());
                    //Log.v("database","------\nprod_id: " + prod_id + "\nprod_name: " + product.getName() + "------\n");
                    List<ProductCatalog.Category.Product.Variants> variantsList = product.getVariants();
                    for (int k = 0; k < variantsList.size(); k++) {
                        ProductCatalog.Category.Product.Variants newVariants = new ProductCatalog.Category.Product.Variants();
                        ProductCatalog.Category.Product.Variants variants = variantsList.get(k);
                        String var_id = variants.getId();
                        newVariants.setId(var_id);
                        newVariants.setColor(variants.getColor());
                        newVariants.setPrice(variants.getPrice());
                        newVariants.setSize(variants.getSize());
                        newVariants.setProd_id(prod_id);
                        //Log.v("database","------\nvar_id: " + var_id + "\nprice: " + variants.getPrice() +  "\nsize: " + variants.getSize() + "------\n");
                        newVariantsList.add(newVariants);
                    }
                    //newProduct.setVariants(variantsList);
                    newProductList.add(newProduct);
                }



                //newCategory.setProducts(productList);
                newCategoryList.add(newCategory);
            }
            //Log.v("database", "\n-------------------------------------");
            insertCategories();
            insertProducts();
            insertVariants();




            return null;
        }

        private void insertProducts() {

            for (int i = 0; i < newProductList.size(); i++) {
                try {
                    dataManager.insertProduct(newProductList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        private void insertCategories() {

            for (int i = 0; i < newCategoryList.size(); i++) {
                Log.v("database", "calling insertCategories");
                try {

                    dataManager.insertCategory(newCategoryList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        private void insertVariants() {

            for (int i = 0; i < newVariantsList.size(); i++) {
                try {
                    dataManager.insertVariant(newVariantsList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //dataManager.setFirstRun("first_run", false);

            apiInterface.getRankings("https://stark-spire-93433.herokuapp.com/json/").enqueue(new Callback<Ranking>() {
                @Override
                public void onResponse(retrofit2.Call<Ranking> call, retrofit2.Response<Ranking> response) {

                    List<Ranking.ProductRanking> rankingList = response.body().productRankings;


                        for (int i = 0; i < rankingList.size(); i++) {

                            String ranking = rankingList.get(i).getRanking();
                            List<Ranking.ProductRanking.Products> rankingProducts = rankingList.get(i).getProducts();
                            if(ranking.equals("Most Viewed Products")){
                                //List<Ranking.ProductRanking.Products> rankingProducts = rankingList.get(i).getProducts();
                                for(int j=0;j<rankingProducts.size();j++){
                                    Ranking.ProductRanking.Products product = new Ranking.ProductRanking.Products();
                                    product.setId(rankingProducts.get(j).getId());
                                    product.setView_count(rankingProducts.get(j).getView_count());
                                    try {
                                        dataManager.insertViewRankings(product);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                            else if(ranking.equals("Most OrdeRed Products")){
                                //List<Ranking.ProductRanking.Products> rankingProducts = rankingList.get(i).getProducts();
                                for(int j=0;j<rankingProducts.size();j++){
                                    Ranking.ProductRanking.Products product = new Ranking.ProductRanking.Products();
                                    product.setId(rankingProducts.get(j).getId());
                                    product.setOrder_count(rankingProducts.get(j).getOrder_count());
                                    try {
                                        dataManager.insertOrderRankings(product);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else if(ranking.equals("Most ShaRed Products")){
                                //List<Ranking.ProductRanking.Products> rankingProducts = rankingList.get(i).getProducts();
                                for(int j=0;j<rankingProducts.size();j++){
                                    Ranking.ProductRanking.Products product = new Ranking.ProductRanking.Products();
                                    product.setId(rankingProducts.get(j).getId());
                                    product.setShare_count(rankingProducts.get(j).getShare_count());
                                    try {
                                        dataManager.insertShareRankings(product);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }


                        }

                    dataManager.setFirstRun("first_run", false);

                }

                @Override
                public void onFailure(retrofit2.Call<Ranking> call, Throwable t) {

                }
            });
        }
    }

    private void populateRecyclerView() {

    }


    @Override
    public void launchIntent(String url) {
        Toast.makeText(mContext, "RecyclerView Row selected", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(activityContext, DetailActivity.class).putExtra("url", url));
    }
}
