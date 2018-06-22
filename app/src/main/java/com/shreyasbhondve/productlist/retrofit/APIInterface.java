package com.shreyasbhondve.productlist.retrofit;
import com.shreyasbhondve.productlist.pojo.Category;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @GET
    Call<ProductCatalog> getCategories(@Url String ur);

    @GET
    Call<ProductCatalog> getRankings();
}
