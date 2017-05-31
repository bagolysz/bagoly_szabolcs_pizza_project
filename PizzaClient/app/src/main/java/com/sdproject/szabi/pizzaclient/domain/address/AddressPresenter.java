package com.sdproject.szabi.pizzaclient.domain.address;

import android.util.Log;

import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.ui.address.IAddressView;
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

public class AddressPresenter implements IAddressPresenter {

    private IAddressView view;
    private String userId;

    public AddressPresenter(IAddressView view) {
        this.view = view;
        userId = PreferencesManager.getInstance().getUser()._id;
    }

    @Override
    public void populateAddressList() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AddressService client = retrofit.create(AddressService.class);
        Call<List<DeliveryAddress>> call = client.getAddresses();

        call.enqueue(new Callback<List<DeliveryAddress>>() {
            @Override
            public void onResponse(Call<List<DeliveryAddress>> call, Response<List<DeliveryAddress>> response) {
                Log.d("address", "successful address request ");

                List<DeliveryAddress> list = new ArrayList<>();
                for (DeliveryAddress item : response.body()) {
                    if (item.userId.equals(userId)) {
                        list.add(item);
                    }
                }

                view.populateAddressList(list);
            }

            @Override
            public void onFailure(Call<List<DeliveryAddress>> call, Throwable t) {
                Log.d("address", "failed to retrieve addresses");
            }
        });

    }

    @Override
    public void addNewAddress(String city, String street, String number, String phone) {
        DeliveryAddress address = new DeliveryAddress(city, userId, street, number, phone);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AddressService client = retrofit.create(AddressService.class);
        Call<DeliveryAddress> call = client.createAddress(address);

        call.enqueue(new Callback<DeliveryAddress>() {
            @Override
            public void onResponse(Call<DeliveryAddress> call, Response<DeliveryAddress> response) {
                Log.d("address", "new address added: id is: " + response.body()._id);
                populateAddressList();
            }

            @Override
            public void onFailure(Call<DeliveryAddress> call, Throwable t) {
                Log.d("address", "failed to add new address");
            }
        });
    }

    @Override
    public void deleteItemFromList(DeliveryAddress item) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AddressService client = retrofit.create(AddressService.class);
        Call<DeliveryAddress> call = client.deleteAddress(item._id);

        call.enqueue(new Callback<DeliveryAddress>() {
            @Override
            public void onResponse(Call<DeliveryAddress> call, Response<DeliveryAddress> response) {
                Log.d("address", "address deleted");
                populateAddressList();
            }

            @Override
            public void onFailure(Call<DeliveryAddress> call, Throwable t) {
                Log.d("address", "failed to deleteaddress");
            }
        });
    }

    @Override
    public void onItemSelected(DeliveryAddress current, int mode) {
        if (mode == Constants.MODE_SELECT) {
            view.finishScreenWithResult(current);
        }

    }
}
