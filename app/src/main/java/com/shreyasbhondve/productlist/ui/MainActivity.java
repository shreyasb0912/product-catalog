package com.shreyasbhondve.productlist.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    private CardView searchByCategoryCardView;

    private EditText searchEditText;

    private ProgressBar progressBar = null;

    private int REQUEST_CODE = 1;

    @Inject
    DataManager dataManager;

    enum Filter{
        MOST_VIEWED,MOST_ORDERED,MOST_SHARED
    }

    Filter filter = Filter.MOST_VIEWED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchByCategoryCardView = findViewById(R.id.searchByCategory);
        searchEditText = findViewById(R.id.searchEdtTxt);
        progressBar = findViewById(R.id.progressBar);

        /**
         * Search functionality
         */
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                List<ProductCatalog.Category.Product> productList = dataManager.searchProducts(keyword);
                populateRecyclerView(productList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * Select category
         */
        searchByCategoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(activityContext, SearchByCategoryActivity.class),REQUEST_CODE);
            }
        });


        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectMainActivity(this);
        recyclerView.setAdapter(recyclerViewAdapter);


        /**
         * firstRun is flag which decides whether to fetch data from server or from local database.
         * If firstRun = true then fetch data from server else fetch data from local database
         */
        boolean firstRun = dataManager.getFirstRun("first_run", true);
        if (firstRun) {
            progressBar.setVisibility(View.VISIBLE);
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
            progressBar.setVisibility(View.GONE);
            List<ProductCatalog.Category.Product> productList = dataManager.getProducts();
            populateRecyclerView(productList);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * Show prducts under the category selected by user
         */
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String cat_id=data.getStringExtra("cat_id");
                List<ProductCatalog.Category.Product> productList = dataManager.getCategorizedProducts(cat_id);
                recyclerViewAdapter.setData(productList);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        /**
         * Sort products according to the count of views, orders or shares.
         */
        switch (item.getItemId()) {
            case R.id.most_viewed:
                filter = Filter.MOST_VIEWED;
                sortRecyclerView();
                return true;
            case R.id.most_ordered:
                filter = Filter.MOST_ORDERED;
                sortRecyclerView();
                return true;
            case R.id.most_shared:
                filter = Filter.MOST_SHARED;
                sortRecyclerView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    /**
     * Function to parse the Categorioes data fetched from the server and populate it in the local database
     */
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

            for (int i = 0; i < categoryList.size(); i++) {
                ProductCatalog.Category newCategory = new ProductCatalog.Category();
                ProductCatalog.Category category = categoryList.get(i);
                String cat_id = category.getId();
                newCategory.setId(cat_id);
                newCategory.setName(category.getName());

                List<ProductCatalog.Category.Product> productList = category.getProducts();
                for (int j = 0; j < productList.size(); j++) {
                    ProductCatalog.Category.Product newProduct = new ProductCatalog.Category.Product();
                    ProductCatalog.Category.Product product = productList.get(j);
                    String prod_id = product.getId();
                    newProduct.setCat_id(cat_id);
                    newProduct.setId(prod_id);
                    newProduct.setName(product.getName());
                    newProduct.setDate_added(product.getDate_added());

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
                        newVariantsList.add(newVariants);
                    }

                    newProductList.add(newProduct);
                }

                newCategoryList.add(newCategory);
            }

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

            /**
             * Function to parse the Ranking data fetched from the server and populate it in the local database
             */
            apiInterface.getRankings("https://stark-spire-93433.herokuapp.com/json/").enqueue(new Callback<Ranking>() {
                @Override
                public void onResponse(retrofit2.Call<Ranking> call, retrofit2.Response<Ranking> response) {

                    List<Ranking.ProductRanking> rankingList = response.body().productRankings;

                        for (int i = 0; i < rankingList.size(); i++) {

                            String ranking = rankingList.get(i).getRanking();
                            List<Ranking.ProductRanking.Products> rankingProducts = rankingList.get(i).getProducts();
                            if(ranking.equals("Most Viewed Products")){

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
                    List<ProductCatalog.Category.Product> productList = dataManager.getProducts();
                    populateRecyclerView(productList);

                    dataManager.setFirstRun("first_run", false);

                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(retrofit2.Call<Ranking> call, Throwable t) {

                }
            });
        }
    }

    /**
     * Function to populate the Products data into local database
     * @param productList
     */
    private void populateRecyclerView(List<ProductCatalog.Category.Product> productList){
        recyclerViewAdapter.setData(productList);
    }

    /**
     * Function to sort the products data according to most viewed, most ordered or most shared
     */
    private void sortRecyclerView() {
        List<ProductCatalog.Category.Product> productList = null;
        switch (filter){
            case MOST_VIEWED:
                productList = dataManager.sortProducts("view_count");
                break;
            case MOST_ORDERED:
                productList = dataManager.sortProducts("order_count");
                break;
            case MOST_SHARED:
                productList = dataManager.sortProducts("share_count");
                break;
        }

        recyclerViewAdapter.setData(productList);
    }


    @Override
    public void launchIntent() {
        Toast.makeText(mContext, "RecyclerView Row selected", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(activityContext, DetailActivity.class));
    }
}
