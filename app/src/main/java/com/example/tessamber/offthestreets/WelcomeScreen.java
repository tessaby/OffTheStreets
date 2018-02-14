package com.example.tessamber.offthestreets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Button bWelcomeLogin = findViewById(R.id.bWelcomLogin);
        bWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), LoginScreen.class);
                startActivityForResult(myIntent, 0);
            }
        });
        Button bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(), RegistrationPage.class);
                startActivityForResult(myIntent2, 0);
            }
        });
        Button bGuest = findViewById(R.id.bGuest);
        bGuest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent3 = new android.content.Intent(view.getContext(), HomeScreen.class);
                startActivityForResult(myIntent3, 0);
            }
        });







    }
}
