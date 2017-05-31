package com.sdproject.szabi.pizzaclient.domain.shoppingcart;

import android.content.pm.LabeledIntent;
import android.util.Log;

import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.datamodel.Order;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;
import com.sdproject.szabi.pizzaclient.domain.address.AddressService;
import com.sdproject.szabi.pizzaclient.ui.shoppingcart.IShoppingCartView;
import com.sdproject.szabi.pizzaclient.utils.Constants;
import com.sdproject.szabi.pizzaclient.utils.PreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Szabi on 5/23/2017.
 */

public class ShoppingCartPresenter implements IShoppingCartPresenter {

    private IShoppingCartView view;
    private DeliveryAddress address;
    private boolean addressSelected;

    public ShoppingCartPresenter(IShoppingCartView view) {
        this.view = view;
        addressSelected = false;
    }

    private void showCartItems(List<ShoppingCartItem> products) {
        view.populateCartList(products);
    }

    private double getTotal(List<ShoppingCartItem> products) {
        double local = 0.0;
        if (products != null) {
            for (ShoppingCartItem item : products) {
                local += item.quantity * item.product.price;
            }
        }
        return local;
    }

    private void setTotal(List<ShoppingCartItem> products) {
        view.setTotal(getTotal(products));
    }

    private void saveCart(List<ShoppingCartItem> products) {
        PreferencesManager.getInstance().putShoppingCart(products);
    }

    @Override
    public void retrieveCartItems() {
        List<ShoppingCartItem> products = PreferencesManager.getInstance().getShoppingCart();
        showCartItems(products);
    }


    @Override
    public void setTotal() {
        List<ShoppingCartItem> products = PreferencesManager.getInstance().getShoppingCart();
        setTotal(products);
    }

    @Override
    public void deleteItemFromCart(ShoppingCartItem item) {
        List<ShoppingCartItem> products = PreferencesManager.getInstance().getShoppingCart();

        int index = -1;
        int size = products.size();
        for (int i = 0; i < size; i++) {
            if (products.get(i).product._id.equals(item.product._id)) {
                index = i;
            }
        }
        if (index != -1)
            products.remove(index);

        setTotal(products);
        showCartItems(products);
        saveCart(products);
    }

    @Override
    public void quantityUp(ShoppingCartItem item) {
        List<ShoppingCartItem> products = PreferencesManager.getInstance().getShoppingCart();
        for (ShoppingCartItem prod : products) {
            if (prod.product._id.equals(item.product._id)) {
                prod.quantity++;
            }
        }
        setTotal(products);
        showCartItems(products);
        saveCart(products);
    }

    @Override
    public void quantityDown(ShoppingCartItem item) {
        List<ShoppingCartItem> products = PreferencesManager.getInstance().getShoppingCart();
        for (ShoppingCartItem prod : products) {
            if (prod.product._id.equals(item.product._id) && prod.quantity > 1) {
                prod.quantity--;
            }
        }
        setTotal(products);
        showCartItems(products);
        saveCart(products);
    }

    @Override
    public void onCartCompleted() {
        List<ShoppingCartItem> products = PreferencesManager.getInstance().getShoppingCart();
        if (products == null || products.size() == 0) {
            view.showMessage("Shopping cart is empty!");
        } else {
            if (!addressSelected) {
                view.startDeliverAddressSelection();
            } else {
                view.finishOrder();
            }
        }
    }

    @Override
    public void onAddressSelected(DeliveryAddress address) {
        addressSelected = true;
        this.address = address;
        view.showAddress(address);
    }

    @Override
    public void addressModification() {
        if (addressSelected) {
            view.startDeliverAddressSelection();
        }
    }

    @Override
    public void confirmOrder() {
        PreferencesManager prefs = PreferencesManager.getInstance();
        Order order = new Order(prefs.getUser()._id, address._id, getTotal(prefs.getShoppingCart()));

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ShoppingCartService client = retrofit.create(ShoppingCartService.class);
        Call<Order> callOrder = client.placeOrder(order);

        callOrder.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.d("order", "new order successfully added");
                view.startListeningForOrderStatusChange(response.body()._id);
                addShoppingCartItems(response.body()._id);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("order", "failed to add new order");
            }
        });
    }

    private void addShoppingCartItems(String orderId){
        List<ShoppingCartItem> list = PreferencesManager.getInstance().getShoppingCart();
        for(ShoppingCartItem item : list){
            item.orderId = orderId;
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ShoppingCartService client = retrofit.create(ShoppingCartService.class);
        Call<List<ShoppingCartItem>> callOrder = client.placeCartItems(list);

        callOrder.enqueue(new Callback<List<ShoppingCartItem>>() {
            @Override
            public void onResponse(Call<List<ShoppingCartItem>> call, Response<List<ShoppingCartItem>> response) {
                Log.d("order", "new cart items successfully added");
                // empty the cart
                saveCart(null);
                // remove selected address;
                addressSelected = false;
                address = null;
                // finish the activity
                view.closeView();
            }

            @Override
            public void onFailure(Call<List<ShoppingCartItem>> call, Throwable t) {
                Log.d("order", "failed to add new cart items");
            }
        });
    }


}
