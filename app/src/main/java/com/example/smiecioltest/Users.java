package com.example.smiecioltest;

import com.google.firebase.database.Exclude;

public class Users {


    private String FName, LName, Email, isAdmin,ProfilePhoto;
    private String Gender;

    public Users(String FName, String LName, String email, String isAdmin, String gender,String ProfileProduct) {
        this.FName = FName;
        this.LName = LName;
        Email = email;
        this.isAdmin = isAdmin;
        ProfilePhoto=ProfileProduct;
        Gender = gender;
    }
    public Users() {

    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
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
