package com.sdproject.szabi.pizzaclient.datamodel;

/**
 * Created by Szabi on 5/21/2017.
 */

public class Category {

    public static final int CATEGORY_PIZZA = 1;
    public static final int CATEGORY_SANDWICH = 2;
    public static final int CATEGORY_SNACKS = 3;
    public static final int CATEGORY_DRINKS = 4;
    public static final int CATEGORY_PROMOTIONS = 5;

    public String _id;
    public String title;
    public int category;

    public Category() {
    }

    public Category(String title, int category) {
        this.title = title;
        this.category = category;
    }

}
