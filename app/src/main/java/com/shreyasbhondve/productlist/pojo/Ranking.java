package com.shreyasbhondve.productlist.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ranking {

    @SerializedName("rankings")
    public List<ProductRanking> productRankings = null;

    public static class ProductRanking{
        @SerializedName("ranking")
        public String ranking;

        @SerializedName("products")
        public List<Products> products = null;

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public List<Products> getProducts() {
            return products;
        }

        public void setProducts(List<Products> products) {
            this.products = products;
        }

        public static class Products{
            @SerializedName("id")
            public String id;

            @SerializedName("view_count")
            public String view_count;

            @SerializedName("order_count")
            public String order_count;

            @SerializedName("shares")
            public String share_count;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getView_count() {
                return view_count;
            }

            public void setView_count(String view_count) {
                this.view_count = view_count;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public String getShare_count() {
                return share_count;
            }

            public void setShare_count(String share_count) {
                this.share_count = share_count;
            }
        }


    }


}
