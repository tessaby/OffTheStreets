package com.example.tessamber.offthestreets.model;

/**
 * Homeless Shelter Class
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


    /**
     * default constructor
     */
    public HomelessShelter() {
    }

    /**
     * Homeless Shelter Constructor
     * @param uniqueKey id
     * @param shelterName name of shelter
     * @param capacity shelter capacity for vacant beds
     * @param restrictions include gender, age, etc
     * @param longitude coordinates
     * @param latitude coordinates
     * @param address location
     * @param specialNotes other restrictions
     * @param phoneNumber shelter phone number
     */
    @SuppressWarnings("ConstructorWithTooManyParameters")
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

    /**
     * getter method for shelter's unique id
     * @return shelter id
     */
    public int getId() { return uniqueKey; }

    /**
     * getter method for shelter's name
     * @return name of shelter
     */
    public String getShelterName() { return shelterName; }

    /**
     * getter method for vacant beds available at shelter
     * @return shelter capacity stored
     */
    public int getCapacity() { return capacity; }

    /**
     *  getter method for restrictions, i.e. gender specified
     * @return shelter restrictions
     */
    public String getRestrictions() { return restrictions; }

    /**
     * getter method for longitude of shelter
     * @return longitude of shelter
     */
    public double getLongitude() { return longitude; }

    /**
     * getter method for shelter latitude
     * @return latitude
     */
    public double getLatitude() { return latitude; }

    /**
     * getter method for shelter address
     * @return address of shelter
     */
    public String getAddress() { return address; }

    /**
     * getter method for shelter notes
     * @return special notes on shelter
     */
    public String getSpecialNotes() { return specialNotes; }

    /**
     * getter method for shelter phone number
     * @return phone number
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * getter method for gender allowed in shelter
     * @return gender restriction specified by shelter, empty string if not specified
     */
    public String getGender() {
        if (restrictions.toLowerCase().contains("men")
                && !restrictions.toLowerCase().contains("women")) {
            return "men";
        } else if (restrictions.toLowerCase().contains("women")) {
            return "women";
        } else {
            return "both";
        }
    }

    /**
     * Set shelter capacity
     * @param cap capacity value
     */
    public void setCapacity(int cap) {
        capacity = cap;
    }

    public String toString() {
        return uniqueKey + " " + shelterName + " " + capacity + " " + restrictions + " "
                + longitude + " " + latitude + " " + address + " " + specialNotes
                + " " + phoneNumber;
    }
}
