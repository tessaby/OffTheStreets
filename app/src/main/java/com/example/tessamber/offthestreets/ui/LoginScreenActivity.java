package com.example.tessamber.offthestreets.ui;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tessamber.offthestreets.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity allows user to enter login details
 * which are verified through firebase authentication database
 */
@SuppressWarnings("CyclicClassDependency")
public class LoginScreenActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // DECLARE an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseUser user;

    // DECLARE email and password fields
    private EditText etUsername;
    private EditText etPassword;
    // DECLARE text view message
    //private TextView tvMessage;
    CallbackManager callbackManager;
    LoginButton login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_screen);
        initializeControls();
        loginWithFB();



        //INITIALIZE:

        // VIEWS
        //email (username)
        etUsername = findViewById(R.id.etUsername);
        //password
        etPassword = findViewById(R.id.etPassword);
        //text view message
        //tvMessage = (TextView) findViewById(R.id.tvMessage);

        // BUTTONS
        Button bLogin = findViewById(R.id.bLogin);
        Button bBack = findViewById(R.id.bBack);

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
            @Override
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
                    android.content.Intent myIntent = new android.content.Intent(view.getContext(),
                            HomeScreenActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent myIntent2 = new android.content.Intent(view.getContext(),
                        WelcomeScreenActivity.class);
                startActivityForResult(myIntent2, 0);
            }
        });
    }

    private void initializeControls() {
        callbackManager = CallbackManager.Factory.create();
        login_button = (LoginButton)findViewById(R.id.login_button);

    }

    private void loginWithFB(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                toastMessage("login successful");
            }

            @Override
            public void onCancel() {
                toastMessage("login cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                toastMessage("login error. try again." + error.getMessage());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * customizable toast
     * @param message message to be displayed
     */

    private void toastMessage(CharSequence message) {
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

        if (!email.isEmpty() && !password.isEmpty()) {
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

}
