package com.sdproject.szabi.pizzaclient.datamodel;

import com.sdproject.szabi.pizzaclient.datamodel.products.Product;

/**
 * Created by Szabi on 5/25/2017.
 */

public class ShoppingCartItem {

    public Product product;
    public int quantity;
    public String orderId;
    public String productId;

    public ShoppingCartItem(){

    }

    public ShoppingCartItem(Product product, int quantity){
        productId = product._id;
        this.product = product;
        this.quantity = quantity;
    }
}
