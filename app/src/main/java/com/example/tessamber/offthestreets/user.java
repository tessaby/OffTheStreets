package com.example.tessamber.offthestreets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tess.amber on 2/21/18.
 * this class stores user info
 */

public class user {

    public static List<String> userTypes = Arrays.asList("user", "admin");

    public static ArrayList<user> MyArr1 = new ArrayList<user>();

    private static int count = 0; //number of users in arraylist
    private String name;
    private String email;
    private String phone;
    private String password;

    public user() {
    }

    public user(String name, String email, String phone, String password) {
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
