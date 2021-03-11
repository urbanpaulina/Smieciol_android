package com.example.smiecioltest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ShowProductToAcceptation_fragment extends Fragment {


    RecyclerView recyclerViewProducts;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_product_to_acceptation_fragment, container, false);

        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerViewProducts= view.findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Query query = firebaseFirestore.collection("Products").orderBy("name");

        FirestoreRecyclerOptions<Products> options = new FirestoreRecyclerOptions.Builder<Products>()
                .setQuery(query,Products.class).build();

        //adapter
        adapter = new FirestoreRecyclerAdapter<Products, ProductViewHolder>(options){

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.products, parent, false);

                return new ProductViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull ShowProductToAcceptation_fragment.ProductViewHolder holder, int position, @NonNull Products model) {
                holder.product_name.setText(model.getName());
                holder.product_weight.setText(model.getWeight());


                System.out.println(model.getName());

            }
        };//adapter
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewProducts.setHasFixedSize(true);
        recyclerViewProducts.setAdapter(adapter);


        return view;
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView product_name;
        TextView product_weight;



        public ProductViewHolder(@NonNull View itemView){

            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_weight = itemView.findViewById(R.id.product_weight);

        }
    }//UserViewHolder

    public ShowProductToAcceptation_fragment() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }
}