package com.sdproject.szabi.pizzaclient.domain.shoppingcart;

import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.datamodel.Order;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Szabi on 5/27/2017.
 */

public interface ShoppingCartService {

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @POST("order")
    Call<Order> placeOrder(@Body Order order);

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @POST("cartitem")
    Call<List<ShoppingCartItem>> placeCartItems(@Body List<ShoppingCartItem> items);
}
