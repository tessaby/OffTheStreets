package com.example.tessamber.offthestreets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tess.amber on 2/21/18.
 * this class stores User info
 */

public class User {

    public static List<String> userTypes = Arrays.asList("User", "admin");

    public static ArrayList<User> MyArr1 = new ArrayList<User>();

    private static int count = 0; //number of users in arraylist
    private String name;
    private String email;
    private String phone;
    private String password;



    public User() {
    }

    //User details constructor
    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        count++;
    }


    public String getEmail() {
        return this.email;
    }

//    public int getCount() {
//        return count;
//    }

    public String getPass() {
        return password;
    }
}
