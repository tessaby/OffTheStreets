package com.example.tessamber.offthestreets.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tessamber.offthestreets.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // INITIALIZE BUTTONS
        Button bWelcomeLogin = findViewById(R.id.bWelcomLogin);
        Button bRegister = findViewById(R.id.bRegister);
        Button bGuest = findViewById(R.id.bGuest);

        bWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(),
                        LoginScreenActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(),
                        RegistrationPageActivity.class);
                startActivityForResult(myIntent2, 0);
            }
        });

        bGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent myIntent3 = new android.content.Intent(view.getContext(),
                        HomeScreenActivity.class);
                startActivityForResult(myIntent3, 0);
            }
        });

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message2");
//
//        myRef.setValue("Hello, World!");
    }

    /**
     * method to make sure previous user is signed out
     */
    @Override
    public void onStart() {
        super.onStart();
        //starting fresh with no previous user sign in..
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (user != null) {
            mAuth.signOut();
            Log.d(TAG, "OnStart: previousUserSignOut: complete");
        }
    }
}
