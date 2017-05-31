package com.sdproject.szabi.pizzaclient.domain.orders;

import android.util.Log;

import com.sdproject.szabi.pizzaclient.datamodel.Order;
import com.sdproject.szabi.pizzaclient.ui.orders.IOrdersView;
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
 * Created by Szabi on 5/24/2017.
 */

public class OrdersPresenter {

    private IOrdersView view;
    private String userId;

    public OrdersPresenter(IOrdersView view) {
        this.view = view;
        userId = PreferencesManager.getInstance().getUser()._id;
    }


    public void retrieveOrders() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        OrdersService client = retrofit.create(OrdersService.class);
        Call<List<Order>> callOrder = client.getOrders();

        callOrder.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                Log.d("order", "successful order request");
                List<Order> list = new ArrayList<>();

                for (Order o : response.body()) {
                    if (o.userId.equals(userId)) {
                        list.add(o);
                    }
                }

                view.populateOrderList(list);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("order", "failed to retrieve orders");
            }
        });
    }
}
