package com.example.tessamber.offthestreets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistrationPage extends AppCompatActivity {
    private EditText etName;
    private EditText etEmailR;
    private EditText etPhoneNum;
    private EditText etPassword;
    private EditText etPassword2;
    private TextView tvRegistration;
    private Spinner spUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        //initializing registration info: name, email, phone number, and password
        etName = (EditText) findViewById(R.id.etName);
        etEmailR = (EditText) findViewById(R.id.etEmailR);
        etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        spUserType = (Spinner) findViewById(R.id.spUserType);

        /*
          Set up the adapter to display the allowable majors in the spinner
         */
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, user.userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserType.setAdapter(adapter);

        Button bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //stores name
                String name = String.valueOf(etName.getText());
                //stores email
                String email = String.valueOf(etEmailR.getText());
                //stores phone number
                String phone = String.valueOf(etPhoneNum.getText());
                //stores password
                String password = String.valueOf(etPassword.getText());
                //stores confirm password
                String password2 = String.valueOf(etPassword2.getText());


                //check that all text boxes are not empty
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    tvRegistration.setText("Registration unsuccessful! Check that all information has been filled in.");

                    //check that password and confirm password text are equal
                } else if (!password.equals(password2)) {
                        tvRegistration.setText("Registration unsuccessful! Passwords do not match.");

                    //store user info in system, return to HomeScreen.
                } else {
                    //check that username does not already exist in list
                    if (user.MyArr1.size() != 0) {
                        boolean flag = true;
                        for (user myuser : user.MyArr1) {
                            if (email.equals(myuser.getEmail())) {
                                tvRegistration.setText("Registration unsuccessful! Email is already registered under another account.");
                                flag = false;
                            }

                            //email not previously registered, allow registration
                        } if (flag) {
                                //add user to database ArrayList
                                user.MyArr1.add(new user(name, email, phone, password));
                                android.content.Intent myIntentRegister = new android.content.Intent(view.getContext(), WelcomeScreen.class);
                                startActivityForResult(myIntentRegister, 0);
                        }
                    } else {
                        //list is empty, add first user to database ArrayList
                        user.MyArr1.add(new user(name, email, phone, password));
                        android.content.Intent myIntentRegister = new android.content.Intent(view.getContext(), WelcomeScreen.class);
                        startActivityForResult(myIntentRegister, 0);
                    }
                }
            }
        });

        Button bCancel = findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), WelcomeScreen.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
