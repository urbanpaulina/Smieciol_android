package com.example.smiecioltest;

import android.os.Bundle;
import android.transition.CircularPropagation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smiecioltest.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.sql.DatabaseMetaData;

import javax.annotation.Nullable;

public class ShowUsers_fragment extends Fragment {
    RecyclerView recyclerViewUsers;
    DatabaseReference db_reference;
    FirebaseFirestore firebaseFirestore;
    FirebaseRecyclerAdapter adapter;
    FirebaseAuth fAuth;
    TextView FName1, email;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_users_fragment, container, false);

        firebaseFirestore= FirebaseFirestore.getInstance();

        recyclerViewUsers= view.findViewById(R.id.recyclerViewUser);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsers.setAdapter(adapter);

        db_reference = FirebaseDatabase.getInstance().getReference().child("Users");
        //db_reference = FirebaseDatabase.getInstance().getReference().child("Products");



//       adapter = new FirebaseRecyclerAdapter<Users, Users_view>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull Users_view holder, int position, @NonNull Users model) {
//               holder.firstName.setText(model.getFname());
//               holder.lastName.setText(model.getLname());
//               holder.email.setText(model.getEmail());


  //          }


        //fAuth = FirebaseAuth.getInstance();
        //userId = fAuth.getCurrentUser().getUid();
       // db_reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        //Query query = firebaseFirestore.collection("Users");

        return view;
    }

    public void onStart(){
        super.onStart();
        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(db_reference,Users.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Users, Users_view>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Users_view holder, int position, @NonNull Users model) {
                holder.firstName.setText(model.getFname());
                holder.lastName.setText(model.getLname());
                holder.email.setText(model.getEmail());


            }

            @NonNull
            @Override
            public Users_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_users,parent, false);
               Users_view users_view= new Users_view(view);
               return users_view;
            }
        };
        recyclerViewUsers.setAdapter(adapter);
        adapter.startListening();
    }

    public static class Users_view extends RecyclerView.ViewHolder{

        TextView firstName, lastName, email;
        //ImageView profileImage;


        public Users_view(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.FName_user);
            lastName = itemView.findViewById(R.id.LName_user);
            email = itemView.findViewById(R.id.Email_user);
            //profileImage = itemView.findViewById(R.id.user_image);
        }
    }

}