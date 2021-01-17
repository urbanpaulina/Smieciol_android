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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Products_fragment extends Fragment {
     RecyclerView product_list;
     DatabaseReference Product_Ref;
     FirebaseAuth pAuth;
     String productID;
//     TextView name, weight;
//     FirebaseFirestore db;
//     FirebaseAuth fAuth;
//     String productID;

    public Products_fragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        name = getActivity().findViewById(R.id.tvRVName);
//        weight = getActivity().findViewById(R.id.tvRVWeight);
//
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_fragment, container, false);

        product_list= view.findViewById(R.id.product_list);
        product_list.setLayoutManager(new LinearLayoutManager(getContext()));

        pAuth = FirebaseAuth.getInstance();
        productID= pAuth.getCurrentUser().getUid();

        Product_Ref = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(Product_Ref, Products.class)
                .build();

        final FirebaseRecyclerAdapter<Products, ProductsViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull Products model) {

//                String productName = dataSnapshot.child("name").getValue().toString();
//                String weight = dataSnapshot.child("status").getValue().toString();

                holder.name.setText(model.getProduct_name());
                holder.weight.setText(model.getWeight());

            }

            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products,parent,false);
                ProductsViewHolder viewHolder = new ProductsViewHolder(view);
                return viewHolder;
            }
        };
        product_list.setAdapter(adapter);
        adapter.startListening();

    }


    public static class ProductsViewHolder extends RecyclerView.ViewHolder{
        TextView name, weight;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.product_name);
            weight= itemView.findViewById(R.id.product_weight);
        }
    }
}