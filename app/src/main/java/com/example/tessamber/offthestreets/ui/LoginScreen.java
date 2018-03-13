package com.example.tessamber.offthestreets.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.User;


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

                //validates the username and password for User, pass
//                if (username.equals("User") && password.equals("pass")) {
//                    android.content.Intent myIntent1 = new android.content.Intent(view.getContext(), HomeScreen.class);
//                    startActivityForResult(myIntent1, 0);


                //validates that email matches one of the registered emails,
                // and if true, that password matches the registered password for that email
                boolean flag = true;
                for (User myuser : User.MyArr1) {
                    if (myuser.getEmail().equals(username)) {
                        if (myuser.getPass().equals(password)) {
                            android.content.Intent myIntent1 = new android.content.Intent(view.getContext(), HomeScreen.class);
                            startActivityForResult(myIntent1, 0);
                            flag = false;
                        }
                    }
                }
                if (flag) {
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
