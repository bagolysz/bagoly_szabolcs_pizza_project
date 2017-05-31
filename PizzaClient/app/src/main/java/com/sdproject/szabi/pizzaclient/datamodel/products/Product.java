package com.sdproject.szabi.pizzaclient.datamodel.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.sdproject.szabi.pizzaclient.datamodel.Category;

import java.net.URI;

/**
 * Created by Szabi on 5/21/2017.
 */

public class Product implements Parcelable {

    public String _id;
    public String title;
    public String description;
    public double price;
    public int productCategory;
    public String imageLocation;

    public Product(){

    }

    public Product(String id, String title, String description, double price, int productCategory, String image){
        this._id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.imageLocation = image;
    }


    protected Product(Parcel in) {
        _id = in.readString();
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        productCategory = in.readInt();
        imageLocation = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(productCategory);
        dest.writeString(imageLocation);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
