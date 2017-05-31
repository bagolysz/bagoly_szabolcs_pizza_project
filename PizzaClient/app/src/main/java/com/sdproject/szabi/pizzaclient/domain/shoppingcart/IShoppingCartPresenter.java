package com.sdproject.szabi.pizzaclient.domain.shoppingcart;

import com.sdproject.szabi.pizzaclient.datamodel.DeliveryAddress;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;

/**
 * Created by Szabi on 5/23/2017.
 */

public interface IShoppingCartPresenter {

    void retrieveCartItems();

    void setTotal();

    void deleteItemFromCart(ShoppingCartItem item);

    void quantityUp(ShoppingCartItem item);

    void quantityDown(ShoppingCartItem item);

    void onCartCompleted();

    void onAddressSelected(DeliveryAddress address);

    void addressModification();

    void confirmOrder();
}
