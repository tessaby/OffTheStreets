package com.example.tessamber.offthestreets.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HomeScreenActivity extends AppCompatActivity {
    private static final String TAG = "OffTheStreets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button bLogout = findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), WelcomeScreenActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button bLoadShelters = findViewById(R.id.bLoadFile);
        bLoadShelters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                readSDFile();
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(), HomelessShelterListActivity.class);
                startActivityForResult(myIntent2, 0);
            }
        });
    }

    private static final String COMMA_DELIMITER = ",";

    /**
     * Open the HomelessShelterDatabase.csv file in the /res/raw directory
     *
     */

//    private int getInts(String str) {
//        int value = Integer.parseInt(str.replaceAll("[\\D]", ""));
//        return value;
//    }

    private void readSDFile() {

        ShelterCollection model = ShelterCollection.INSTANCE;

        try {
            //Open a stream on the raw file
            InputStream is = getResources().openRawResource(R.raw.shelterdatabase);
            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            br.readLine(); //get rid of header line
            int count = 0;
            while ((line = br.readLine()) != null) {
                Log.d(HomeScreenActivity.TAG, line);
                String[] shelterDetails = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                //HomelessShelter(int uniqueKey,String shelterName, int capacity,
                // String restrictions, double longitude, double latitude, String address,
                // String specialNotes, String phoneNumber)

//                for(int i = 0; i < shelterDetails.length; i++) {
//                    System.out.println("ShelterDetails[" + i + "]: " + shelterDetails[i]);
//                }
                int passInt = 0;
                String address = "";
                String notes = "";
                if(!(shelterDetails[2].equals(""))) {
                    passInt = Integer.parseInt(shelterDetails[2].replaceAll("[\\D]", ""));
                }
                if(!(shelterDetails[6].equals(""))) {
                    address = shelterDetails[6].replaceAll("\"", "");
                }
                if(!(shelterDetails[7].equals(""))) {
                    notes = shelterDetails[7].replaceAll("\"", "");
                }
                System.out.println(address);

                HomelessShelter shelter = new HomelessShelter(Integer.parseInt(shelterDetails[0]),
                        shelterDetails[1], passInt, shelterDetails[3],
                        Double.parseDouble(shelterDetails[4]),
                        Double.parseDouble(shelterDetails[5]),
                        address, notes, shelterDetails[8]);

                model.addShelter(shelter);
            }
            br.close();
        } catch (IOException e) {
            Log.e(HomeScreenActivity.TAG, "error reading assets", e);
        }
    }
}
