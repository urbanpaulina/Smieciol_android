package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public class Products_fragment extends Fragment {
    RecyclerView product_list;
    DatabaseReference Product_Ref;
    FirebaseFirestore firebaseFirestore;
    //FirebaseRecyclerAdapter adapter;
    product_adapter product_adapter;


    public Products_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_fragment, container, false);

        product_list= view.findViewById(R.id.product_list);
        product_list.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseFirestore= FirebaseFirestore.getInstance();


        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), Products.class)
                        .build();

        product_adapter= new product_adapter(options);
        product_list.setAdapter(product_adapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        product_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        product_adapter.stopListening();
    }

}