package com.example.tessamber.offthestreets.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "OffTheStreets";

    // DECLARE BUTTONS
    Button bLogout;
    Button bLoadShelters;

    // DECLARE FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // INITIALIZE:

        // BUTTONS
        bLogout = findViewById(R.id.bLogout);
        bLoadShelters = findViewById(R.id.bLoadFile);

        // FIREBASE Authentication
        mAuth = FirebaseAuth.getInstance();

        bLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Signing Out...");
                System.out.println(FirebaseAuth.getInstance().getCurrentUser() == null);


                android.content.Intent myIntent = new android.content.Intent(view.getContext(), WelcomeScreenActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        bLoadShelters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                readSDFile();
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(), HomelessShelterListActivity.class);
                startActivityForResult(myIntent2, 0);
            }
        });
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //READ CSV FILE OF HOMELESS SHELTERS.
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


//    public FirebaseUser getFirebaseUser() {
//        return FirebaseAuth.getInstance().getCurrentUser();
//    }


}
