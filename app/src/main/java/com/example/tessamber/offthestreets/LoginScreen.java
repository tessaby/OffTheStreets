package com.example.tessamber.offthestreets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginScreen extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //initializing email and password
        //email (username)
        etUsername = (EditText) findViewById(R.id.etUsername);
        //password
        etPassword = (EditText) findViewById(R.id.etPassword);
        //text view message
        tvMessage = (TextView) findViewById(R.id.tvMessage);

        Button bLogin = findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //stores username
                String username = String.valueOf(etUsername.getText());
                //stores password
                String password = String.valueOf(etPassword.getText());

                //validates the username and password for user, pass
                if (username.equals("user") && password.equals("pass")) {
                    android.content.Intent myIntent1 = new android.content.Intent(view.getContext(), HomeScreen.class);
                    startActivityForResult(myIntent1, 0);
                } else {
                    tvMessage.setText("Login Unsuccessful!");
                }
            }
        });

        Button bBack = findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(), WelcomeScreen.class);
                startActivityForResult(myIntent2, 0);
            }
        });






    }
}
