package com.example.tessamber.offthestreets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tess.amber on 2/21/18.
 * this class stores User info
 */

public class User {

    public static List<String> userTypes = Arrays.asList("User", "admin");

    public static ArrayList<User> MyArr1 = new ArrayList<User>();

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

    public User() {
    }

    //User details constructor
    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        count++;
    }

    public User(int uniqueKey,String shelterName, int capacity, String restrictions,
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
