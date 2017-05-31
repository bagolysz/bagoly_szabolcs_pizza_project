package com.sdproject.szabi.pizzaclient.ui.home;

import com.sdproject.szabi.pizzaclient.datamodel.Category;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;

import java.util.List;

/**
 * Created by Szabi on 5/21/2017.
 */

public interface IHomeView {
    void populateProductsList(List<Product> products);

    void populateCategoryList(List<Category> categories);
}
