package com.sdproject.szabi.pizzaclient.domain.orders;

import com.sdproject.szabi.pizzaclient.datamodel.Order;
import com.sdproject.szabi.pizzaclient.datamodel.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Szabi on 5/28/2017.
 */

public interface OrdersService {

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @GET("order?sort=datetime&dir=-1")
    Call<List<Order>> getOrders();

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @GET("order/{id}")
    Call<Order> getOrderById(@Path("id") String id);

}
