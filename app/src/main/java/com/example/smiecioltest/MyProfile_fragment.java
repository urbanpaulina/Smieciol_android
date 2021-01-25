package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyProfile_fragment extends Fragment implements View.OnClickListener {

    TextView FName, email;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    String userId;
    //GridLayout mainGrid;
    CardView card1,card2,card3,card4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile_fragment, container, false);

        card1= view.findViewById(R.id.editProfile);
//        card2= view.findViewById(R.id.addProduct);
//        card3= view.findViewById(R.id.scanReceipts);
//        card4= view.findViewById(R.id.ranking);

        card1.setOnClickListener(this);
//        card2.setOnClickListener(this);
//        card3.setOnClickListener(this);
//        card4.setOnClickListener(this);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        email = getActivity().findViewById(R.id.tv_email);
        FName = getActivity().findViewById(R.id.tv_fName);
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
                    String emailResult = task.getResult().getString("Email");
                    String nameResult = task.getResult().getString("FName");

                    email.setText(emailResult);
                    FName.setText(nameResult);
                } else {
                    Intent intent = new Intent(getActivity(), Register.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

       Intent intent;

        switch (v.getId()){
            case R.id.editProfile :
                intent = new Intent(v.getContext(), Edit_profile.class);
                //intent = new Intent(this,EditProfile_fragment.class);
                startActivity(intent);
                break;

        }

    }
}