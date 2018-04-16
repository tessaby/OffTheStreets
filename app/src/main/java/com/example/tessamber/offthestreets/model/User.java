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

    @SuppressWarnings("FieldMayBeFinal")
    private static int count = 0; //number of users in arraylist
    private String name;
    private String email;
    private String phone;
    private String password;
    private int bedsBooked = 0;
    private String shelterBookedAt = "" ;

    /**
     * default User constructor
     */
    public User() {
    }

    /**
     * User details constructor
     * @param name user's name
     * @param email user's email
     * @param phone user's number
     * @param password user's password
     */
    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    /**
     * getter method for user's email
     * @return email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * getter method for password
     * @return user's password
     */
    public String getPass() {
        return password;
    }

    /**
     * getter method for name
     * @return name of user
     */
    public String getName() { return name; }

    /**
     * getter method for phone number
     * @return user's phone number
     */
    public String getPhoneNumber() { return phone; }

    /**
     * getter method for number of beds booked
     * @return number of beds booked by user
     */
    public int getBedsBooked() {
        return bedsBooked;
    }

    /**
     * getter method for homeless shelter user booked at
     * @return shelter booked at
     */
    public String getShelterBookedAt() {
        return shelterBookedAt;
    }

    /**
     * method called when clearing user's bed booking.
     */
    public void clearBooking() {
        this.clearBedsBooked();
        this.clearShelterBookAt();
    }

    private void clearBedsBooked() { bedsBooked = 0; }

    private void clearShelterBookAt() { shelterBookedAt = null; }

}
