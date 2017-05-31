package com.sdproject.szabi.pizzaclient.domain.home;

import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Szabi on 5/27/2017.
 */

public interface ProductService {

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @GET("category")
    Call<List<Category>> getCategories();

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @GET("products")
    Call<List<Product>> getProducts();
}
