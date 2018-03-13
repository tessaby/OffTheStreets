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
}
