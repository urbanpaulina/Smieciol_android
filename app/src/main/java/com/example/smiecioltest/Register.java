package com.example.smiecioltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPasswordcreate, etConfirmPassword;
    Switch sw_gender;
    Button btn_createAccount, btn_registered;
    FirebaseAuth fAuth;


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        btn_createAccount = findViewById(R.id.btn_createAccount);
        etPasswordcreate = findViewById(R.id.etPasswordcreate);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        sw_gender = findViewById(R.id.sw_gender);
        btn_registered = findViewById(R.id.btn_registered);


        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPasswordcreate.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                boolean gender = sw_gender.isChecked();

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPasswordcreate.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    etConfirmPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    etPasswordcreate.setError("Password must be >=6");
                    return;
                }
                if (!password.equals(confirmPassword) ) {
                    etConfirmPassword.setError("Passwords must be identical");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = fAuth.getCurrentUser();
                        Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                        DocumentReference df = db.collection("Users").document(user.getUid());
                        Map<String, Object> userInfo= new HashMap<>();
                        userInfo.put("FName",etFirstName.getText().toString());
                        userInfo.put("LName",etLastName.getText().toString());
                        userInfo.put("Email",etEmail.getText().toString());
                        userInfo.put("Gender",sw_gender.getText().toString());

                        userInfo.put("isAdmin", "1");

                        df.set(userInfo);

                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = fAuth.getCurrentUser();
//                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
//                            DocumentReference df = db.collection("Users").document(user.getUid());
//                            Map<String, Object> userInfo= new HashMap<>();
//                            userInfo.put("FName",etFirstName.getText().toString());
//                            userInfo.put("LName",etLastName.getText().toString());
//                            userInfo.put("Email",etEmail.getText().toString());
//                            userInfo.put("Gender",sw_gender.getText().toString());
//
//                            userInfo.put("isUser", "1");
//
//                            df.set(userInfo);
//
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        } else {
//                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
                //db.collection("users").document(ID).set(user);
            }
        });

        btn_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}