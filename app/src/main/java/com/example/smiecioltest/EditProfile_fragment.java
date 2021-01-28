package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfile_fragment extends Fragment {

    TextView FName,LName,Email;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    //DatabaseReference
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
        return inflater.inflate(R.layout.fragment_edit_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        //email = getActivity().findViewById(R.id.tv_email);
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

//    public void update(View view){
//
//        if(isFirstNameChange() || isLastNameChange() || isEmailChange()){
//            //Toast.makeText(this,"Data has been updated", Toast.LENGTH_LONG).show();
//            Toast.makeText(EditProfile_fragment.this.getActivity().getApplicationContext() , "Data has been updated", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private boolean isEmailChange() {
//    }
//
//    private boolean isLastNameChange() {
//    }
//
//    private boolean isFirstNameChange() {
//    }
}