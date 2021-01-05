package com.example.smiecioltest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class ShowUsers_fragment extends Fragment {
    RecyclerView recyclerViewUsers;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    TextView FName1, email;
    String userId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_users_fragment, container, false);
        email = (TextView) view.findViewById(R.id.tvRVEmail);
        FName1 = (TextView) view.findViewById(R.id.tvRVFNameLName);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(userId);
        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                email.setText(value.getString("Email"));
                FName1.setText(value.getString("FName"));
            }
        });



        // Inflate the layout for this fragment
//        fAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//        recyclerViewUsers = (RecyclerView) view.findViewById(R.id.recyclerViewUser);
//        //Query--------------
//
//        Query query = db.collection("Users").orderBy("email");
//        FirestoreRecyclerOptions <User> options = new FirestoreRecyclerOptions.Builder<User>().setQuery(query,User.class).build();

        return view;
    }
}