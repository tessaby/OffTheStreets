package com.example.tessamber.offthestreets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tess.amber on 2/21/18.
 * this class stores user info
 */

public class user {

    public static List<String> userTypes = Arrays.asList("user", "admin");

    public static ArrayList<user> MyArr1 = new ArrayList<user>();

    private static int count = 0; //number of users in arraylist
    private String name;
    private String email;
    private String phone;
    private String password;

    //variables according to header list
    private int uniqueKey;
    private String shelterName;
    private int capacity;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String specialNotes;
    private String phoneNumber;

    public user() {
    }

    //user details constructor
    public user(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        count++;
    }

    public user(int uniqueKey, String shelterName, int capacity, String restrictions,
                double longitude, double latitude, String address, String specialNotes,
                String phoneNumber) {

        this.uniqueKey = uniqueKey;
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.specialNotes = specialNotes;
        this.phoneNumber = phoneNumber;

    }

    public String getEmail() {
        return this.email;
    }

//    public int getCount() {
//        return count;
//    }

    public String getPass() {
        return password;
    }
}
