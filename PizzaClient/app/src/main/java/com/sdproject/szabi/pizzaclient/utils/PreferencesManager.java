package com.sdproject.szabi.pizzaclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdproject.szabi.pizzaclient.datamodel.ShoppingCartItem;
import com.sdproject.szabi.pizzaclient.datamodel.User;
import com.sdproject.szabi.pizzaclient.datamodel.products.Product;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Szabi on 5/25/2017.
 */

public class PreferencesManager {

    private static final String SETTINGS_NAME = "default_settings";
    private static PreferencesManager preferencesManager;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private enum Key {SHOPPING_CART, LOGIN_USER, TEST_INT}

    private PreferencesManager(Context context) {
        prefs = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesManager getInstance(Context context) {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager(context.getApplicationContext());
        }
        return preferencesManager;
    }

    public static PreferencesManager getInstance() {
        if (preferencesManager != null) {
            return preferencesManager;
        }

        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    public void putShoppingCart(List<ShoppingCartItem> cart) {
        doEdit();
        String json = new Gson().toJson(cart);
        editor.putString(Key.SHOPPING_CART.toString(), json);
        doCommit();
    }

    public List<ShoppingCartItem> getShoppingCart() {
        String json = prefs.getString(Key.SHOPPING_CART.toString(), null);
        Type type = new TypeToken<List<ShoppingCartItem>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public void putUser(User user){
        doEdit();
        String json = new Gson().toJson(user);
        editor.putString(Key.LOGIN_USER.toString(), json);
        doCommit();
    }

    public User getUser(){
        String json = prefs.getString(Key.LOGIN_USER.toString(), null);
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }

    public void putNumber(int number) {
        doEdit();
        editor.putInt(Key.TEST_INT.toString(), number);
        doCommit();
    }

    public int getNumber() {
        return prefs.getInt(Key.TEST_INT.toString(), 0);
    }


    private void doEdit() {
        if (editor == null) {
            editor = prefs.edit();
        }
    }

    private void doCommit() {
        if (editor != null) {
            editor.commit();
            editor = null;
        }
    }
}
