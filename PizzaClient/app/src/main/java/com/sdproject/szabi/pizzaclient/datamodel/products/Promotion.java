package com.sdproject.szabi.pizzaclient.datamodel.products;

import com.sdproject.szabi.pizzaclient.datamodel.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 5/21/2017.
 */

public class Promotion extends Product {

    private List<Product> products;

    public Promotion(){

    }

    public Promotion(String title, String description, double price, int productCategory) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;

        products = new ArrayList<>();
    }

    public Promotion(String title, String description, double price, int productCategory, List<Product> products) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.products = products;
    }

    public void addProduct(Product item){
        products.add(item);
    }

    public List<Product> getProducts(){
        return products;
    }
}
