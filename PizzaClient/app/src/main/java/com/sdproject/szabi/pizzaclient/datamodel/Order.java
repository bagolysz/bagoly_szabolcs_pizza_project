package com.sdproject.szabi.pizzaclient.datamodel;

import java.util.Date;

/**
 * Created by Szabi on 5/25/2017.
 */

public class Order {

    public static final int STATUS_WAIT = 1;
    public static final int STATUS_PREPARING = 2;
    public static final int STATUS_DELIVERING = 3;
    public static final int STATUS_FINISHED = 4;

    public String _id;
    public String userId;
    public String addressId;
    public double total;
    public Date datetime;
    public int status;

    public Order(){
    }

    public Order(String userId, String addressId, double total){
        this.userId = userId;
        this.addressId = addressId;
        this.total = total;
        datetime = new Date(System.currentTimeMillis() - 7*60*60*1000); // silly android, need time correction
        status = STATUS_WAIT;
    }

}
