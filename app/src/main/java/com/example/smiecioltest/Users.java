package com.example.smiecioltest;

import android.widget.ImageView;

public class Users {

    public String Fname, Lname, email;
    public ImageView profileImage;

    public Users()
    {

    }

    public Users(String name, String status, String image) {
        this.Fname = Fname;
        this.Lname = Lname;
        this.email = email;
        this.profileImage= profileImage;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String Fname) {
        Fname = Fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String Lname) {

        Lname = Lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
