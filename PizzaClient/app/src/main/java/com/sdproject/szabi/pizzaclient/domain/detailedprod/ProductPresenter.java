package com.sdproject.szabi.pizzaclient.domain.detailedprod;

import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;
import com.sdproject.szabi.pizzaclient.ui.detailedprod.IProductView;
import com.sdproject.szabi.pizzaclient.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 5/22/2017.
 */

public class ProductPresenter implements IProductPresenter {

    private IProductView view;

    private Product product;
    private int quantity;
    private double subTotal;

    public ProductPresenter(IProductView view, Product product) {
        this.view = view;

        this.product = product;

        quantity = 1;
        subTotal = product.price;

        view.setImage(product.imageLocation);
        view.setTitle(product.title);
        view.setDescription(product.description);
        view.updateFields(quantity, subTotal);
    }

    @Override
    public void quantityUp() {
        quantity++;
        subTotal += product.price;
        view.updateFields(quantity, subTotal);
    }

    @Override
    public void quantityDown() {
        if (quantity > 1) {
            quantity--;
            subTotal -= product.price;
            view.updateFields(quantity, subTotal);
        }
    }

    @Override
    public void addProductToShoppingCart() {
        PreferencesManager prefs = PreferencesManager.getInstance();
        List<ShoppingCartItem> cart = prefs.getShoppingCart();
        if (cart == null)
            cart = new ArrayList<>();

        // if product already in shopping cart just increment the quantity, else add it to the cart
        boolean found = false;
        for (ShoppingCartItem item : cart){
            if (item.product._id.equals(product._id)){
                item.quantity += quantity;
                found = true;
            }
        }

        if (!found){
            cart.add(new ShoppingCartItem(product, quantity));
        }
        prefs.putShoppingCart(cart);

    }
}
