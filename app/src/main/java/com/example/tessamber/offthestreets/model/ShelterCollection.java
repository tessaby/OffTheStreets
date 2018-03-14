package com.example.tessamber.offthestreets.model;

import android.util.Log;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.ui.HomeScreen;
import com.example.tessamber.offthestreets.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

    public void addShelter(HomelessShelter shelter) {
        shelters.add(shelter);
    }

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

    private List<HomelessShelter> searchByGender(String gender) {
        List<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelters.size(); i++) {
            HomelessShelter shelt = shelters.get(i);
            // the replace all is so it can match famillies with newborns"
            if(shelt.getGender().equalsIgnoreCase(gender)) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }
    private List<HomelessShelter> searchByAgeRange(String ageRange) {
        List<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelters.size(); i++) {
            HomelessShelter shelt = shelters.get(i);
            // the replace all is so it can match famillies with newborns"
            if(shelt.getRestrictions().replaceAll("w/", "with").toLowerCase()
                    .indexOf(ageRange.toLowerCase()) != -1 ) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }
    private List<HomelessShelter> searchByName(String name) {
        List<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelters.size(); i++) {
            HomelessShelter shelt = shelters.get(i);
            // the replace all is so it can match famillies with newborns"
            if(shelt.getShelterName().equalsIgnoreCase(name)) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }

    public ArrayList<HomelessShelter> searchShelterList(
                                                        String gender, String ageRange, String name) {
        ArrayList<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelters.size(); i++) {
            HomelessShelter shelt = shelters.get(i);
            // the replace all is so it can match famillies with newborns"
            if((ageRange.equalsIgnoreCase("all") || ageRange.equalsIgnoreCase("anyone") ||
                    shelt.getRestrictions().replaceAll("w/", "with").toLowerCase()
                            .indexOf(ageRange.toLowerCase()) != -1 )&& (name.equals("") ||
                    shelt.getShelterName().equalsIgnoreCase(name)) && (
                    gender.equalsIgnoreCase("both") ||
                            shelt.getGender().equalsIgnoreCase(gender))) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }
    /*
    private void readSDFile() {
        ShelterCollection model = ShelterCollection.INSTANCE;
        BufferedReader br = null;
        try {
            //Open a stream on the raw file
            InputStream is = getResources().openRawResource(R.raw.shelterdatabase);
            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            //br = new BufferedReader(new FileReader("shelterdatabase.csv"));

            String line;
            br.readLine(); //get rid of header line
            int count = 0;
            while ((line = br.readLine()) != null) {
                Log.d(HomeScreen.TAG, line);
                String[] shelterDetails = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                //HomelessShelter(int uniqueKey,String shelterName, int capacity,
                // String restrictions, double longitude, double latitude, String address,
                // String specialNotes, String phoneNumber)

                //for(int i = 0; i < shelterDetails.length; i++) {
                //    System.out.println("ShelterDetails[" + i + "]: " + shelterDetails[i]);
                //}
                int passInt = 0;
                if(!(shelterDetails[2].equals(""))) {
                    passInt = Integer.parseInt(shelterDetails[2].replaceAll("[\\D]", ""));
                }
                HomelessShelter shelter = new HomelessShelter(Integer.parseInt(shelterDetails[0]),
                        shelterDetails[1], passInt, shelterDetails[3],
                        Double.parseDouble(shelterDetails[4]),
                        Double.parseDouble(shelterDetails[5]),
                        shelterDetails[6], shelterDetails[7], shelterDetails[8]);
                shelters.add(shelter);
            }
            br.close();



        } catch (IOException e) {
            Log.e(HomeScreen.TAG, "error reading assets", e);
        }

    }*/
}
