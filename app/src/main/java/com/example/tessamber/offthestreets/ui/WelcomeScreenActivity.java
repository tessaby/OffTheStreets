package com.example.tessamber.offthestreets.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tessamber.offthestreets.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Button bWelcomeLogin = findViewById(R.id.bWelcomLogin);
        bWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), LoginScreenActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        Button bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(), RegistrationPageActivity.class);
                startActivityForResult(myIntent2, 0);
            }
        });
        Button bGuest = findViewById(R.id.bGuest);
        bGuest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent3 = new android.content.Intent(view.getContext(), HomeScreenActivity.class);
                startActivityForResult(myIntent3, 0);
            }
        });







    }
}
