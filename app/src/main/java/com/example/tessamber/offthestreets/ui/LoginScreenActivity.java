package com.example.tessamber.offthestreets.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginScreenActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // DECLARE an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    // DECLARE email and password fields
    private EditText etUsername;
    private EditText etPassword;
    // DECLARE text view message
    //private TextView tvMessage;

    // DECLARE buttons
    Button bLogin;
    Button bBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //INITIALIZE:

        // VIEWS
        //email (username)
        etUsername = (EditText) findViewById(R.id.etUsername);
        //password
        etPassword = (EditText) findViewById(R.id.etPassword);
        //text view message
        //tvMessage = (TextView) findViewById(R.id.tvMessage);

        // BUTTONS
        bLogin = findViewById(R.id.bLogin);
        bBack = findViewById(R.id.bBack);

        // FIREBASE Authentication
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in " + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: no user is currently signed in");
                    //toastMessage("Successfully signed out.");
                }
                //...
            }
        };

        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get username
                String email = String.valueOf(etUsername.getText());
                //get password
                String password = String.valueOf(etPassword.getText());

                // FIREBASE DATABASE EMAIL/PASSWORD AUTHENTICATION
                if (!isUserSignedIn()) {
                    Log.d(TAG, "There is no current user so sign in.");
                    signIn(email, password);
                }

                //CHECKING CURRENT USER LOGIN STATUS TO CHANGE TO HOMEPAGE
                Log.d(TAG, "current user is not null: " + isUserSignedIn());
                if (isUserSignedIn()) {
                    android.content.Intent myIntent = new android.content.Intent(view.getContext(), HomeScreenActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(), WelcomeScreenActivity.class);
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

    private void signIn(String email, String password) {
        Log.d(TAG, "signing in with:" + email);

        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in successful
                                toastMessage("Successfully signed in!");
                                Log.d(TAG, "signInWithEmail: successful");

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail: failed", task.getException());
                                toastMessage("SignIn unsuccessful! Authentication failed.");
                            }
                        }
                    });
        } else {
            toastMessage("All fields must be filled.");
        }
    }

    private boolean isUserSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    //HARDCODED TESTER "USER" & "PASS".
    //validates the username and password for User, pass
//                if (username.equals("User") && password.equals("pass")) {
//                    android.content.Intent myIntent1 = new android.content.Intent(view.getContext(), HomeScreenActivity.class);
//                    startActivityForResult(myIntent1, 0);

    //CHECKS ARRAYLIST<User> STORAGE METHOD.
    //validates that email matches one of the registered emails,
    // and if true, that password matches the registered password for that email
//                boolean flag = true;
//                for (User myuser : User.MyArr1) {
//                    if (myuser.getEmail().equals(email)) {
//                        if (myuser.getPass().equals(password)) {
//                            flag = false;
//                            android.content.Intent myIntent1 = new android.content.Intent(view.getContext(), HomeScreenActivity.class);
//                            startActivityForResult(myIntent1, 0);
//                        }
//                    }
//                }
//                if (flag) {
//                    tvMessage.setText("Login Unsuccessful!");
//                }
//            }
}
