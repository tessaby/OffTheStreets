package com.example.tessamber.offthestreets.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Shelter Collection Class contains list of shelters
 */
public class ShelterCollection {

    public static final ShelterCollection INSTANCE = new ShelterCollection();

    private ArrayList<HomelessShelter> shelters;

    /**
     * Shelter collection constructor
     * assigns shelter list
     */
    public ShelterCollection() {
        shelters = new ArrayList<>();
    }

    /**
     * method adds shelter to shelter list in shelter collection
     * @param shelter to be added to list
     */
    public void addShelter(HomelessShelter shelter) { shelters.add(shelter); }

    /**
     * method to empty shelter list in Shelter Collection
     */
    public void clearShelterList() { shelters.clear(); }

    /**
     * getter method for shelter list
     * @return list of shelters
     */
    public ArrayList<HomelessShelter> getShelters() { return shelters; }

    /**
     * retrieves HomelessShelter from shelter list by id
     * @param id of shelter
     * @return shelter found
     */
    public HomelessShelter findItemById(int id) {
        for (HomelessShelter h : shelters) {
            if (h.getId() == id) { return h; }
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }

    /**
     * method to search for specific shelter
     * @param name name of Homeless shelter
     * @return arraylist with shelter with specified name if it exists in the list
     */
    private List<HomelessShelter> searchByName(String name) {
        List<HomelessShelter> displayList = new ArrayList<>();
        for (int i = 0; i < shelters.size(); i++) {
            HomelessShelter shelt = shelters.get(i);
            // the replace all is so it can match families with newborns"
            if(shelt.getShelterName().equalsIgnoreCase(name)) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }

    /**
     * helper method that searches through arraylist of shelters based on specified gender
     * @param s specific search to check
     * @param gender specified gender
     * @return true if the shelter satisfies criteria, false otherwise
     */
    private boolean searchForGender(HomelessShelter s, String gender) {
        //if its empty return true
        if (gender.isEmpty()) {
            return true;
        }
        //if it says men or women.. check if it matches the homeless shelter restriction..
        if ("women".equalsIgnoreCase(gender) || "men".equalsIgnoreCase(gender)) {
            //differentiate between men and women...
            return (s.getGender().equalsIgnoreCase(gender));
        } else {
            return !("men".equalsIgnoreCase(s.getGender()))
                    && !("women".equalsIgnoreCase(s.getGender()));
        }
        //if it doesn't say men or women, don't bother, everything is true..
        // unless it specifies children..?
    }

    /**
     * helper method checks if shelter takes in requested age range
     * @param s specified shelter
     * @param ageRange specified age group
     * @return true if it does, false otherwise
     */
    @SuppressWarnings("SimplifiableIfStatement")
    private boolean searchForAgeRange(HomelessShelter s, String ageRange) {
        if ("Anyone".equalsIgnoreCase(ageRange)) {
            return true ;
        } else if (ageRange.toLowerCase().contains("children")
                && s.getRestrictions().toLowerCase().contains("children")) {
            return true;
        } else if (ageRange.toLowerCase().contains("newborns")
                && s.getRestrictions().toLowerCase().contains("newborns")) {
            return true;
        } else {
            return (ageRange.toLowerCase().contains("young adults")
                    && s.getRestrictions().toLowerCase().contains("young adults"));
        }
    }

    /**
     * method that searches through list of shelters based on search criteria entered by user
     * @param gender restrictions
     * @param ageRange restrictions
     * @param name specific shelter name requested
     * @return ArrayList of shelters satisfying search criteria
     */
    public List<HomelessShelter> searchShelterList(String gender, String ageRange, String name) {

        List<HomelessShelter> displayList = new ArrayList<>();
        // if all the search boxes are empty
        if (TextUtils.isEmpty(gender) && TextUtils.isEmpty(ageRange) && TextUtils.isEmpty(name)) {
            Log.d("ifSearchBoxesAreEmpty", "display whole list of shelters");
            return shelters;
        }
        if (!TextUtils.isEmpty(name)) {
            Log.d("searchByName", "display shelter");
            return searchByName(name);
        }
        //run through the list, add if it equals this & this..
        boolean flag1;
        boolean flag2;
        for (HomelessShelter s : shelters) {

            flag1 = (gender.isEmpty() || searchForGender(s, gender));
            flag2 = (ageRange.isEmpty() || searchForAgeRange(s, ageRange));

            if (flag1 && flag2) { displayList.add(s); }
        }
        return displayList;
    }

    /**
     * Method called to write complete list of shelterS to FireBase database
     * OffTheStreets child homeless_shelters
     * @param shelters ArrayList of shelters from csv file
     */
    public static void addShelterCollectionToFirebase(Iterable<HomelessShelter> shelters) {

        FirebaseDatabase hFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference hDatabaseReference = hFirebaseDatabase
                .getReference("OffTheStreetsDatabase");
        DatabaseReference sheltersRef = hDatabaseReference.child("homeless_shelters");
        int key;
        for (HomelessShelter hs : shelters) {
            key = hs.getId();
            sheltersRef.child(key + hs.getShelterName()).setValue(hs);
        }
    }

    /**
     * method that helps to update shelter capacity based on firebase
     * @param shelterID shelter's unique id
     * @param newCapacity capacity to be stored for shelter in shelter list
     */
    public static void updateCapacity(int shelterID, int newCapacity) {
        HomelessShelter s = INSTANCE.findItemById(shelterID);
        s.setCapacity(newCapacity);
    }
}
