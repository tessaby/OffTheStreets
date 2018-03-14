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
    private int longitude;
    private int latitude;
    private String address;
    private String specialNotes;
    private String phoneNumber;


    //Homeless Shelter Constructor
    public HomelessShelter(int uniqueKey, String shelterName, int capacity, String restrictions,
                int longitude, int latitude, String address, String specialNotes,
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
    public int getLongitude() { return longitude; }
    public int getLatitude() { return latitude; }
    public String getAddress() { return address; }
    public String getSpecialNotes() { return specialNotes; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getGender() {
        if (restrictions.toLowerCase().indexOf("men") != -1) {
            return "men";
        } else if (restrictions.toLowerCase().indexOf("women") != -1) {
            return "women";
        } else {
            return "both";
        }
    }
}
