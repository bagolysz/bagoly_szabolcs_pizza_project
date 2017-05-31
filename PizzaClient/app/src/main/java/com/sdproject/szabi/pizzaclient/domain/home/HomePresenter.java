package com.sdproject.szabi.pizzaclient.domain.home;

import android.util.Log;

import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;
import com.sdproject.szabi.pizzaclient.ui.home.IHomeView;
import com.sdproject.szabi.pizzaclient.utils.Constants;
import com.sdproject.szabi.pizzaclient.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Szabi on 5/21/2017.
 */

public class HomePresenter implements IHomePresenter {
    private IHomeView view;

    public HomePresenter(IHomeView view) {
        this.view = view;
    }

    @Override
    public void retrieveOffers() {
        retrieveOffers(Category.CATEGORY_PIZZA);
    }

    @Override
    public void retrieveOffers(final int category) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService client = retrofit.create(ProductService.class);
        Call<List<Product>> call = client.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d("retrofit", "successful request");

                List<Product> list = new ArrayList<>();
                if (response.body() != null) {
                    for (Product p : response.body()) {
                        if (p.productCategory == category) {
                            list.add(p);
                        }
                    }
                }

                view.populateProductsList(list);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("retrofit", "request failure!");
            }
        });
    }

    @Override
    public void clearUser() {
        PreferencesManager.getInstance().putUser(null);
        PreferencesManager.getInstance().putShoppingCart(null);
    }

    @Override
    public void retrieveCategories() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService client = retrofit.create(ProductService.class);
        Call<List<Category>> call = client.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d("retrofit", "successful request");
                view.populateCategoryList(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("retrofit", "request failure!");
            }
        });
    }
}
