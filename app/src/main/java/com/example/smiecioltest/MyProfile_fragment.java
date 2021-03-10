package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyProfile_fragment extends Fragment implements View.OnClickListener {

    TextView FName;
    ImageView ProfilePhoto;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    String userId;
    //GridLayout mainGrid;
    CardView card1,card2,card3,card4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile_fragment, container, false);

        card1= view.findViewById(R.id.editProfile);
        card2= view.findViewById(R.id.addProduct);
        card3= view.findViewById(R.id.scanReceipts);
        card4= view.findViewById(R.id.ranking);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        FName = getActivity().findViewById(R.id.tv_fName);
        ProfilePhoto = getActivity().findViewById(R.id.profilePhoto);
    }

    @Override
    public void onStart() {
        super.onStart();
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        DocumentReference documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    //String emailResult = task.getResult().getString("Email");
                    String nameResult = task.getResult().getString("FName");
                    String photoResult = task.getResult().getString("ProfilePhoto");


                    FName.setText(nameResult);
                    Picasso.get().load(photoResult).placeholder(R.drawable.ic_my_profile).into(ProfilePhoto);

                } else {
                    Intent intent = new Intent(getActivity(), Register.class);
                    startActivity(intent);
                }
            }
        });
    }

//
//    StorageReference mountainImagesRef = storageReference.child("images/");
//
//// While the file names are the same, the references point to different files
//mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false



    @Override
    public void onClick(View v) {

       Intent intent;

        switch (v.getId()){
            case R.id.editProfile :
                getFragmentManager().beginTransaction().replace(R.id.Fragment_container,new EditProfile_fragment()).commit();
                break;
            case R.id.addProduct :
                checkUserAccessLevel(userId);
                //getFragmentManager().beginTransaction().replace(R.id.Fragment_container,new AddProduct_fragment()).commit();
                break;
            case R.id.scanReceipts :
                getFragmentManager().beginTransaction().replace(R.id.Fragment_container,new ScanReceipt_fragment()).commit();
                break;
            case R.id.ranking :
                getFragmentManager().beginTransaction().replace(R.id.Fragment_container,new Ranking_fragment()).commit();
                break;


        }

    }

    private void checkUserAccessLevel(String uid){
        DocumentReference df = db.collection("Users").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+ documentSnapshot.getData());
                //identify the user access level

                if(documentSnapshot.getString("isAdmin")== null){
                    //user is not admin
                    getFragmentManager().beginTransaction().replace(R.id.Fragment_container,new AddProductUser_fragment()).commit();

                }

                else  {

                }
            }
        });
    }
}