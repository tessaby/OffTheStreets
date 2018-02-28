package com.example.tessamber.offthestreets;

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
            List<user> userList = new ArrayList<user>();
            String line = "";
            br.readLine(); //read header line, must handle IOException

            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(COMMA_DELIMITER);

                if (userDetails.length > 0) {

                    //user(int uniqueKey,String shelterName, int capacity, String restrictions,
                    //double longitude, double latitude, String address, String specialNotes,
                          //  String phoneNumber)

                    user user = new user(Integer.parseInt(userDetails[0]), userDetails[1],
                            Integer.parseInt(userDetails[2]), userDetails[3],
                            Double.parseDouble(userDetails[4]), Double.parseDouble(userDetails[5]),
                            userDetails[6], userDetails[7], userDetails[8]);

                    userList.add(user);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }



    }

}