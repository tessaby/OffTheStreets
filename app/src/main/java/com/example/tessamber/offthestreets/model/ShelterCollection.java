package com.example.tessamber.offthestreets.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tess.amber on 3/13/18.
 */

public class ShelterCollection {

    public static final ShelterCollection INSTANCE = new ShelterCollection();

    private List<HomelessShelter> shelters;

    private ShelterCollection() {
        shelters = new ArrayList<>();
    }

    public void addShelter(HomelessShelter shelter) {
        shelters.add(shelter);
    }

    public List<HomelessShelter> getShelters() {
        return shelters;
    }

    public HomelessShelter findItemById(int id) {
        for (HomelessShelter h : shelters) {
            if (h.getId() == id) return h;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }

    private List<HomelessShelter> searchShelters(String gender, String ageRange, String name) {
        //IF Gender can be either, "both" should be passed in
        //FOR age range, assume you can only search for one age category at a time
        //families with newborns
        //Children
        //Young Adults
        //Anyone
        //Name is self explanatory,
        List<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelters.size(); i++) {
            HomelessShelter shelt = shelters.get(i);
            // the replace all is so it can match famillies with newborns"
            if(shelt.getRestrictions().replaceAll("w/", "with").toLowerCase()
                    .indexOf(ageRange.toLowerCase()) != -1 &&
                    shelt.getShelterName().equalsIgnoreCase(name) &&
                    shelt.getGender().equalsIgnoreCase(gender)) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }
}
