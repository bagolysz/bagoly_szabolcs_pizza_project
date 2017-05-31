package com.sdproject.szabi.pizzaclient.domain;

import android.util.Log;

import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.datamodel.User;
import com.sdproject.szabi.pizzaclient.domain.address.AddressService;
import com.sdproject.szabi.pizzaclient.domain.home.ProductService;
import com.sdproject.szabi.pizzaclient.utils.Constants;
import com.sdproject.szabi.pizzaclient.utils.PreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Szabi on 5/27/2017.
 */

public class LoginPresenter {

    public void addUserToDatabase(final User user) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UserService client = retrofit.create(UserService.class);
        Call<List<User>> call = client.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("retrofit", "successful request users");
                checkUser(response.body(), user);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("retrofit", "request failure users!");
            }
        });
        PreferencesManager.getInstance().putUser(user);
    }

    private void checkUser(List<User> list, User user){
        boolean exists = false;
        if (list != null){
            for (User local : list){
                if (local.email.equals(user.email)){
                    exists = true;
                    PreferencesManager.getInstance().putUser(local);
                }
            }
        }

        if (!exists){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            UserService client = retrofit.create(UserService.class);
            Call<User> call = client.addUser(user);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d("address", "new user added. id is: " + response.body()._id);
                    PreferencesManager.getInstance().putUser(response.body());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("address", "failed to add new user");
                }
            });
        }
    }
}
