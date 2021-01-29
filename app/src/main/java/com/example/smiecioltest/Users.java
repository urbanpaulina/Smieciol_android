package com.example.smiecioltest;

import com.google.firebase.database.Exclude;

public class Users {


    private String FName, LName, Email, isAdmin;
    private String Gender;

    public Users(String FName, String LName, String email, String isAdmin, String gender) {
        this.FName = FName;
        this.LName = LName;
        Email = email;
        this.isAdmin = isAdmin;
        Gender = gender;
    }
    public Users() {

    }
    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }

    public String getEmail() {
        return Email;
    }

    public String getIsAdmin() {
        return isAdmin;
    }


    public void setFName(String FName) {
        this.FName = FName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getGender() {
        return Gender;
    }
}
