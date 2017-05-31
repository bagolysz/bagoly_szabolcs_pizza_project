package com.sdproject.szabi.pizzaclient.domain.address;

import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Szabi on 5/27/2017.
 */

public interface AddressService {

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @GET("address")
    Call<List<DeliveryAddress>> getAddresses();

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @POST("address")
    Call<DeliveryAddress> createAddress(@Body DeliveryAddress address);

    @Headers("apikey: 8c698d6c91ead67e5f0b81abe8ee0ce3fbf71")
    @DELETE("address/{id}")
    Call<DeliveryAddress> deleteAddress(@Path("id") String id);
}
