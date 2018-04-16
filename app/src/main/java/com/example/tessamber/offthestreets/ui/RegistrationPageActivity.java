package com.example.tessamber.offthestreets.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationPageActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    // DECLARE an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference usersRef;

    // DECLARE TEXT VIEWS
    private EditText etName;
    private EditText etEmailR;
    private EditText etPhoneNum;
    private EditText etPassword;
    private EditText etPassword2;
    private TextView tvRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // INITIALIZE REGISTRATION INFO:
        // name, email, phone number, and password
        etName = findViewById(R.id.etName);
        etEmailR = findViewById(R.id.etEmailR);
        etPhoneNum = findViewById(R.id.etPhoneNum);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        tvRegistration = findViewById(R.id.tvRegistration);
        Spinner spUserType = findViewById(R.id.spUserType);

        // INITIALIZE BUTTONS
        Button bRegister = findViewById(R.id.bRegister);
        Button bCancel = findViewById(R.id.bCancel);

        // INITIALIZE FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        //INITIALIZE FIREBASE REFERENCES for users database
        FirebaseDatabase uFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference uDatabaseReference = uFirebaseDatabase.getReference("OffTheStreetsDatabase");
        usersRef = uDatabaseReference.child("users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                //...
            }
        };

        // SET UP SPINNER LAYOUT FOR USERTYPES
        /*
          Set up the adapter to display the allowable majors in the spinner
         */
        @SuppressWarnings("unchecked") ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, User.userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserType.setAdapter(adapter);

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

                //store User info in FIREBASE, enter HomeScreenActivity.
            } else {
                User user = new User(name, email, phone, password);
                registerUser(user);
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                System.out.println(currentUser == null);
                String ref = EncodeString(email);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    usersRef.child(ref).setValue(user);
                    android.content.Intent myIntent = new android.content.Intent(view.getContext(), HomeScreenActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent = new android.content.Intent(view.getContext(), WelcomeScreenActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    private String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public String DecodeString(String string) {
        return string.replace(",", ".");
    }

    /**
     * customizable toast
     * @param message message to be displayed
     */
    @SuppressWarnings("SameParameterValue")
    private void toastMessage(@SuppressWarnings("SameParameterValue") String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * private method to help validate email and register user to authentication for Firebase
     * also adds user info to users database which is the child of OffTheStreetsDatabase
     * @param user the user to be registered
     */

    private void registerUser(User user) {
        final String email = user.getEmail();
        final String pass = user.getPass();
        System.out.println("trying to register user");


        //validate that email does not already exist in user database...
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            System.out.println("task was successful");
                            //gets the currently signed in user
                            //ADD USER INFO TO:
                            //OffTheStreetsDatabase:users:
                            signIn(email, pass);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            toastMessage("Authentication failed.");
                        }
                    }
                });
    }

    /**
     * signing in after successful registration
     * @param email of user registering
     * @param password of user registering
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in successful
                            Log.d(TAG, "signInWithEmail: successful");
                            System.out.println("signed in after registering.");
                            //toastMessage("Successfully signed in!");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail: failed", task.getException());
                            //toastMessage("SignIn unsuccessful! Authentication failed.");
                            System.out.println("signing in after registration failed.");
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    //ADD USER TO ARRAYLIST<USER> METHOD
//    //check that username does not already exist in list
//                if (MyArr1.size() != 0) {
//        boolean flag = true;
//        for (User myuser : MyArr1) {
//            if (email.equals(myuser.getEmail())) {
//                tvRegistration.setText("Registration unsuccessful! Email is already registered under another account.");
//                flag = false;
//            }
//
//            //email not previously registered, allow registration
//              MyArr1.add(new User(name, email, phone, password));
//        }
//        if (flag) {
//            //add User to database ArrayList
////                        MyArr1.add(new User(name, email, phone, password));
////                        usersRef.child(email).setValue(new User(name, email, phone, password));
//            writeNewUser(name, email, phone, password, usersRef);
//
//            android.content.Intent myIntentRegister = new android.content.Intent(view.getContext(), WelcomeScreenActivity.class);
//            startActivityForResult(myIntentRegister, 0);
//        }
//    } else {
//        //list is empty, add first User to database ArrayList
////                    MyArr1.add(new User(name, email, phone, password));
////                    usersRef.child(email).setValue(new User(name, email, phone, password));
//        android.content.Intent myIntentRegister = new android.content.Intent(view.getContext(), WelcomeScreenActivity.class);
//        startActivityForResult(myIntentRegister, 0);
//    }

}
