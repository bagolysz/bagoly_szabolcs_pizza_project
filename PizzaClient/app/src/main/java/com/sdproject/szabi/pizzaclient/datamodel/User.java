package com.sdproject.szabi.pizzaclient.datamodel;

/**
 * Created by Szabi on 5/25/2017.
 */

public class User {

    public String _id;
    public String name;
    public String email;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
