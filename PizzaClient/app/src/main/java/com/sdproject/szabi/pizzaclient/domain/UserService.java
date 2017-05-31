package com.sdproject.szabi.pizzaclient.domain;

import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.datamodel.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Szabi on 5/27/2017.
 */

public interface UserService {

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @GET("user")
    Call<List<User>> getUsers();

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @POST("user")
    Call<User> addUser(@Body User user);
}
