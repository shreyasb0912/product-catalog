package com.shreyasbhondve.productlist.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {

    @SerializedName("categories")
    public List<Category> categories = null;

    public static class Category {

        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("products")
        public List<Product> products = null;

//        @SerializedName("rankings")
//        public ArrayList<Product.Ranking> rankings = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

//        public ArrayList<Product.Ranking> getRankings() {
//            return rankings;
//        }
//
//        public void setRankings(ArrayList<Product.Ranking> rankings) {
//            this.rankings = rankings;
//        }

        public static class Product {

            @SerializedName("id")
            public String id;

            @SerializedName("name")
            public String name;

            @SerializedName("date_added")
            public String date_added;

            @SerializedName("variants")
            public List<Variants> variants = null;

            public String cat_id;

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setDate_added(String date_added) {
                this.date_added = date_added;
            }

            public void setVariants(List<Variants> variants) {
                this.variants = variants;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getCat_id() {
                return cat_id;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getDate_added() {
                return date_added;
            }

            public List<Variants> getVariants() {
                return variants;
            }

            public static class Variants {
                @SerializedName("id")
                public String id;
                @SerializedName("color")
                public String color;
                @SerializedName("size")
                public String size;
                @SerializedName("price")
                public String price;

                public String prod_id;

                public String getProd_id() {
                    return prod_id;
                }

                public void setProd_id(String prod_id) {
                    this.prod_id = prod_id;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }
            }

//            public static class Ranking {
//
//                @SerializedName("ranking")
//                public String ranking;
//
//                @SerializedName("rankings")
//                public List<Products> products;
//
//                public List<Products> getProducts() {
//                    return products;
//                }
//
//                public void setProducts(List<Products> products) {
//                    this.products = products;
//                }
//
//                public class Products{
//                    @SerializedName("id")
//                    public String id;
//
//                    @SerializedName("view_count")
//                    public String view_count;
//
//                    @SerializedName("order_count")
//                    public String order_count;
//
//                    @SerializedName("share_count")
//                    public String share_count;
//
//                    public String getId() {
//                        return id;
//                    }
//
//                    public void setId(String id) {
//                        this.id = id;
//                    }
//
//                    public String getView_count() {
//                        return view_count;
//                    }
//
//                    public void setView_count(String view_count) {
//                        this.view_count = view_count;
//                    }
//
//                    public String getOrder_count() {
//                        return order_count;
//                    }
//
//                    public void setOrder_count(String order_count) {
//                        this.order_count = order_count;
//                    }
//
//                    public String getShare_count() {
//                        return share_count;
//                    }
//
//                    public void setShare_count(String share_count) {
//                        this.share_count = share_count;
//                    }
//                }
//
//                public String getRanking() {
//                    return ranking;
//                }
//
//                public void setRanking(String ranking) {
//                    this.ranking = ranking;
//                }
//
//
//
//            }
        }
    }

}
