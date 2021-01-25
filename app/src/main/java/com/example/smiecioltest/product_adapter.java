package com.example.smiecioltest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class product_adapter extends FirebaseRecyclerAdapter<Products,product_adapter.ProductsViewHolder> {


    public product_adapter(@NonNull FirebaseRecyclerOptions<Products> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull Products model) {
        holder.productName.setText(model.getName());
        holder.productWeight.setText(model.getWeight());

        System.out.println(model.getName());
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products, parent, false);

        return new ProductsViewHolder(view);
    }

    public  class ProductsViewHolder extends RecyclerView.ViewHolder{
        TextView productName, productWeight;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName= itemView.findViewById(R.id.product_name);
            productWeight= itemView.findViewById(R.id.product_weight);
        }
    }
}
