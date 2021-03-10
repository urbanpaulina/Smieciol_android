package com.example.smiecioltest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AddProductUser_fragment extends Fragment implements View.OnClickListener {


    EditText NameProduct, WeightProduct,Barcode;
    Button addProduct;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    DatabaseReference Product_Ref;
    String productId;


    public AddProductUser_fragment() {
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
        db = FirebaseFirestore.getInstance();

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


            Toast.makeText(AddProductUser_fragment.this.getActivity().getApplicationContext(), "Product has been added", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onClick(View v) {

        addproduct();
        Map<String, Object> product = new HashMap<>();
        product.put("name", NameProduct.getText().toString());
        product.put("weight", WeightProduct.getText().toString());
        product.put("barcode", Barcode.getText().toString());

        db.collection("ProductsToAcceptation").document()
                .set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


}