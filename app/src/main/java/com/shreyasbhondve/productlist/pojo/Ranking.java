package com.shreyasbhondve.productlist.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ranking {

    @SerializedName("ranking")
    public String ranking;

    @SerializedName("products")
    public List<Product> products = null;

    public class Product {

        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("date_added")
        public String date_added;

        @SerializedName("variants")
        public List<Category.Product.Variants> variants = null;



        public class Variants{
            @SerializedName("id")
            public String id;
            @SerializedName("color")
            public String color;
            @SerializedName("size")
            public String size;
            @SerializedName("price")
            public String price;
        }
    }
}
