package com.sdproject.szabi.pizzaclient.ui.shoppingcart;

import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;

import java.util.List;

/**
 * Created by Szabi on 5/23/2017.
 */

public interface IShoppingCartView {
    void populateCartList(List<ShoppingCartItem> categories);

    void setTotal(double total);

    void startDeliverAddressSelection();

    void finishOrder();

    void showAddress(DeliveryAddress address);

    void showMessage(String msg);

    void closeView();

    void startListeningForOrderStatusChange(String id);
}
