package com.example.tessamber.offthestreets.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tess.amber on 2/21/18.
 * this class stores User info
 */

@SuppressWarnings("unused")
public class User {

    public static final List<String> userTypes = Arrays.asList("user", "admin");

    @SuppressWarnings("PublicField")
    public static List<User> MyArr1 = new ArrayList<>();

    private static int count = 0; //number of users in arraylist
    private String name;
    private String email;
    private String phone;
    private String password;
    private int bedsBooked = 0;
    private String shelterBookedAt = "" ;

    public User() {
    }

    //User details constructor
    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return password;
    }

    public String getName() { return name; }

    public String getPhoneNumber() { return phone; }

    public int getBedsBooked() {
        return bedsBooked;
    }

    public String getShelterBookedAt() {
        return shelterBookedAt;
    }

    public void clearBooking() {
        this.clearBedsBooked();
        this.clearShelterBookAt();
    }

    private void clearBedsBooked() { bedsBooked = 0; }

    private void clearShelterBookAt() { shelterBookedAt = null; }

}
