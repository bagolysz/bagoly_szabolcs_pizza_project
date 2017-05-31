package com.sdproject.szabi.pizzaclient.ui.detailedprod;

/**
 * Created by Szabi on 5/22/2017.
 */

public interface IProductView {

    void updateFields(int quantity, double subTotal);

    void setImage(String imageLocation);

    void setDescription(String description);

    void setTitle(String title);
}
