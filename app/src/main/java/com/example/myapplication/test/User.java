package com.example.myapplication.test;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class User implements Serializable {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
