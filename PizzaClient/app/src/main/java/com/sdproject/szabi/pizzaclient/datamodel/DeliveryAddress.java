package com.sdproject.szabi.pizzaclient.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Szabi on 5/24/2017.
 */

public class DeliveryAddress implements Parcelable {

    public String _id;
    public String userId;
    public String city;
    public String street;
    public String number;
    public String phoneNumber;

    public DeliveryAddress() {

    }

    public DeliveryAddress(String city, String userId, String street, String number, String phoneNumber) {
        this.city = city;
        this.userId = userId;
        this.street = street;
        this.number = number;
        this.phoneNumber = phoneNumber;
    }

    protected DeliveryAddress(Parcel in) {
        _id = in.readString();
        userId = in.readString();
        city = in.readString();
        street = in.readString();
        number = in.readString();
        phoneNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(userId);
        dest.writeString(city);
        dest.writeString(street);
        dest.writeString(number);
        dest.writeString(phoneNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DeliveryAddress> CREATOR = new Parcelable.Creator<DeliveryAddress>() {
        @Override
        public DeliveryAddress createFromParcel(Parcel in) {
            return new DeliveryAddress(in);
        }

        @Override
        public DeliveryAddress[] newArray(int size) {
            return new DeliveryAddress[size];
        }
    };
}
