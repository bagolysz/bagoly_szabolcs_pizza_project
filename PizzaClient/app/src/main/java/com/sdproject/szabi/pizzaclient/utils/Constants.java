package com.sdproject.szabi.pizzaclient.utils;

import android.support.annotation.IntDef;

import com.sdproject.szabi.pizzaclient.datamodel.Order;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Szabi on 5/27/2017.
 */

public class Constants {

    public static final int MODE_VIEW = 0;
    public static final int MODE_SELECT = 1;

    public static final String BASE_URL = "https://pizzamanager-542e.restdb.io/rest/";
    public static final String API_KEY = "8c698d6c91ead67e5f0b81abe8ee0ce3fbf71";

    public static String getOrderStatus(int code){
        switch (code){
            case Order.STATUS_WAIT:
                return "Waiting to process";
            case Order.STATUS_PREPARING:
                return "Preparing order";
            case Order.STATUS_DELIVERING:
                return "Delivering";
            case Order.STATUS_FINISHED:
                return "Finished";
        }
        return "Unknown";
    }
}
