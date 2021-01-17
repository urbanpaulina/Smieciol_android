package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText etLogin, etPassword;
    Button btn_login, btn_create;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.btn_login);
        btn_create = findViewById(R.id.btn_create);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etLogin.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    etLogin.setError("Email is required");

                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    etPassword.setError("Password must be >=6");
                    return;
                }

                fAuth.signInWithEmailAndPassword(etLogin.getText().toString(),etPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
    //-------------------------------------------------------------------------------------------------

    private void checkUserAccessLevel(String uid){
        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+ documentSnapshot.getData());
                //identify the user access level

                if(documentSnapshot.getString("isAdmin")!=null){
                        //user is admin
                    startActivity(new Intent(getApplicationContext(), Admin.class));//----------------
                    finish();
                }

                else  {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
//    }
}