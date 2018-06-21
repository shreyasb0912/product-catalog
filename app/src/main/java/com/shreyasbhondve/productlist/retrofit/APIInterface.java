package com.shreyasbhondve.productlist.retrofit;
import com.shreyasbhondve.productlist.pojo.Category;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @GET
    Call<Category> getCategories(@Url String ur);

    @GET
    Call<Category> getRankings();
}
