package com.example.tessamber.offthestreets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Button bCancel = findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), WelcomeScreen.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
