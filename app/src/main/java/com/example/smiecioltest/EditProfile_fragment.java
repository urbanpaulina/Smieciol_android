package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import com.example.smiecioltest.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfile_fragment extends Fragment implements View.OnClickListener {

    EditText FName, LName, Email;
    Button updateButton;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    Users users;
    String userId;


    public EditProfile_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile_fragment, container, false);
        updateButton= view.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        FName = getActivity().findViewById(R.id.etFirstName);
        LName = getActivity().findViewById(R.id.etLastName);
        Email = getActivity().findViewById(R.id.etEmail);

    }

    @Override
    public void onStart() {
        super.onStart();
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    String FirstNameResult = task.getResult().getString("FName");
                    String LastNameResult = task.getResult().getString("LName");
                    String emailResult = task.getResult().getString("Email");

                    FName.setText(FirstNameResult);
                    LName.setText(LastNameResult);
                    Email.setText(emailResult);

                } else {
                    Intent intent = new Intent(getActivity(), Register.class);
                    startActivity(intent);
                }
            }
        });
    }


    private boolean hasValidationErrors(String FirstName, String LastName, String email) {
        if (FirstName.isEmpty()) {
            FName.setError("First name required");
            FName.requestFocus();
            return true;
        }

        if (LastName.isEmpty()) {
            LName.setError("Last name required");
            LName.requestFocus();
            return true;
        }

        if (email.isEmpty()) {
            Email.setError("Email required");
            Email.requestFocus();
            return true;
        }
        return false;
    }

    public void updateUsers() {
        String FirstName = FName.getText().toString().trim();
        String LastName = LName.getText().toString().trim();
        String email = Email.getText().toString().trim();

        if (!hasValidationErrors(FirstName, LastName, email)) {

            //Users users = new Users();
            
            db.collection("Users").document(userId)
                    .update(
                            "FName", FirstName,
                            "LName", LastName,
                            "Email", email

                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditProfile_fragment.this.getActivity().getApplicationContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateButton:
                updateUsers();
                break;
        }
    }
}