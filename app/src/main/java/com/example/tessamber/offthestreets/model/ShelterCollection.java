package com.example.tessamber.offthestreets.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tess.amber on 3/13/18.
 */

public class ShelterCollection {

    public static final ShelterCollection INSTANCE = new ShelterCollection();

    private ArrayList<HomelessShelter> shelters;

    private ShelterCollection() {
        shelters = new ArrayList<>();
    }

    public void addShelter(HomelessShelter shelter) { shelters.add(shelter);
    }

    public void clearShelterList() { shelters.clear(); }

    public ArrayList<HomelessShelter> getShelters() {
        return shelters;
    }

    public HomelessShelter findItemById(int id) {
        for (HomelessShelter h : shelters) {
            if (h.getId() == id) return h;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }

    /**
     * method to search for specific shelter
     * @param name name of Homeless shelter
     * @return arraylist with shelter with specified name if it exists in the list
     */
    private ArrayList<HomelessShelter> searchByName(String name) {
        ArrayList<HomelessShelter> displayList = new ArrayList<>();
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
        if (gender.equalsIgnoreCase("women") || gender.equalsIgnoreCase("men")) {
            //differentiate between men and women...
            return (s.getGender().equalsIgnoreCase(gender));
        } else {
            return !(s.getGender().equalsIgnoreCase("men"))
                    && !(s.getGender().equalsIgnoreCase("women"));
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
    private boolean searchForAgeRange(HomelessShelter s, String ageRange) {
        if (ageRange.equalsIgnoreCase("Anyone")) {
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

        ArrayList<HomelessShelter> displayList = new ArrayList<>();
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

    public static void updateCapacity(int shelterID, int newCapacity) {
        HomelessShelter s = INSTANCE.findItemById(shelterID);
        s.setCapacity(newCapacity);
    }
}
