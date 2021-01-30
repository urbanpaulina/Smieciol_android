package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddProduct_fragment extends Fragment implements View.OnClickListener {

    EditText NameProduct, WeightProduct,Barcode;
    Button addProduct;
    FirebaseAuth fAuth;
    FirebaseFirestore db_product;
    DatabaseReference Product_Ref;
    String productId;


    public AddProduct_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_product_fragment, container, false);

        NameProduct = view.findViewById(R.id.etName);
        WeightProduct = view.findViewById(R.id.etWeight);
        Barcode = view.findViewById(R.id.etBarcode);
        addProduct= view.findViewById(R.id.addButton);
        addProduct.setOnClickListener(this);


        fAuth = FirebaseAuth.getInstance();

        Product_Ref = FirebaseDatabase.getInstance().getReference().child("products");

        return view;

    }

    private boolean hasValidationErrors(String name, String weight, String barcode) {
        if (name.isEmpty()) {
            NameProduct.setError("Product name is required");
            NameProduct.requestFocus();
            return true;
        }
        if (weight.isEmpty()) {
            WeightProduct.setError("Weight is required");
            WeightProduct.requestFocus();
            return true;
        }
        if (barcode.isEmpty()) {
            Barcode.setError("Barcode is required");
            Barcode.requestFocus();
            return true;
        }
        if (barcode.length() < 12) {
            Barcode.setError("Barcode must have 12 numbers");
            return true;
        }
        if (barcode.length() > 12) {
            Barcode.setError("Barcode must have 12 numbers");
            return true;
        }
       return false;
    }

    public  void addproduct() {

        String name = NameProduct.getText().toString();
        String weight = WeightProduct.getText().toString();
        String barcode = Barcode.getText().toString();


        if (!hasValidationErrors(name, weight, barcode)) {


            name = NameProduct.getText().toString();
            weight = WeightProduct.getText().toString();
            barcode = Barcode.getText().toString();
            Products products = new Products(name,weight,barcode);
            Product_Ref.push().setValue(products);


            Toast.makeText(AddProduct_fragment.this.getActivity().getApplicationContext(), "Product has been added", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onClick(View v) {
       addproduct();
    }
}



