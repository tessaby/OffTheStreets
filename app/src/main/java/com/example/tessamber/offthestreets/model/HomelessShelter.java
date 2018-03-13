package com.example.tessamber.offthestreets.model;

/**
 * Created by tess.amber on 3/13/18.
 */

public class HomelessShelter {

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


    //Homeless Shelter Constructor
    public HomelessShelter(int uniqueKey, String shelterName, int capacity, String restrictions,
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

    public int getId() { return uniqueKey; }
    public String getShelterName() { return shelterName; }
    public int getCapacity() { return capacity; }
    public String getRestrictions() { return restrictions; }
    public double getLLongitude() { return longitude; }
    public double getLatitude() { return latitude; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
}
