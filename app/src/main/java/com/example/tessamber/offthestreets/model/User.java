package com.example.tessamber.offthestreets.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tess.amber on 2/21/18.
 * this class stores User info
 */

public class User {

    public static List<String> userTypes = Arrays.asList("user", "admin");

    public static ArrayList<User> MyArr1 = new ArrayList<User>();

    private static int count = 0; //number of users in arraylist
    private String name;
    private String email;
    private String phone;
    private String password;
    private int bedsBooked = 0;
    private Booking booking;

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

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return password;
    }

    public String getName() { return name; }

    public String getPhoneNumber() { return phone; }

    public int getNumberOfBedsBooked() { return bedsBooked; }

    public void clearBedsBooked() { bedsBooked = 0; }

    public void setBooking(int shelterID, int number) {
        HomelessShelter s = new ShelterCollection.INSTANCE.findItemById(shelterID);
        booking = new Booking(number, s);
        bedsBooked += number;
    }

    public class Booking {
        private int beds;
        private HomelessShelter shelterBooked;

        public Booking() {
        }

        public Booking(int beds, HomelessShelter shelter) {
            this.beds = beds;
            shelterBooked = shelter;
        }

        public void clearBooking() {
            beds = 0;
            shelterBooked = null;
        }
    }
}
