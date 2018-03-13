package com.example.tessamber.offthestreets.model;

import com.example.tessamber.offthestreets.model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tess.amber on 2/26/18.
 */

public class ReadCSVFile_BufferedReader {

    private static final String COMMA_DELIMITER = ",";

    public static void main(String args[]) {

        BufferedReader br = null;

        //use try catch to handle file not found exception
        try {

            br = new BufferedReader(new FileReader("Homeless Shelter Database.csv"));
            List<HomelessShelter> shelterList = new ArrayList<HomelessShelter>();
            String line = "";
            br.readLine(); //read header line, must handle IOException

            while ((line = br.readLine()) != null) {
                String[] shelterDetails = line.split(COMMA_DELIMITER);

                if (shelterDetails.length > 0) {

                    //User(int uniqueKey,String shelterName, int capacity, String restrictions,
                    //double longitude, double latitude, String address, String specialNotes,
                          //  String phoneNumber)

                    HomelessShelter shelter = new HomelessShelter(Integer.parseInt(userDetails[0]), userDetails[1],
                            Integer.parseInt(shelterDetails[2]), shelterDetails[3],
                            Double.parseDouble(shelterDetails[4]), Double.parseDouble(shelterDetails[5]),
                            shelterDetails[6], shelterDetails[7], shelterDetails[8]);

                    shelterList.add(shelter);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }



    }

}