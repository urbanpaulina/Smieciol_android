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

public class ShowUsers_fragment extends Fragment {
    RecyclerView recyclerViewUsers;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_users_fragment, container, false);

        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerViewUsers= view.findViewById(R.id.recyclerViewUser);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = firebaseFirestore.collection("Users").orderBy("Email");

        FirestoreRecyclerOptions<Users> options = new FirestoreRecyclerOptions.Builder<Users>()
                .setQuery(query,Users.class).build();

        //adapter
        adapter = new FirestoreRecyclerAdapter<Users, UserViewHolder>(options){

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.show_users, parent, false);

                return new UserViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Users model) {
                holder.FName_user.setText(model.getFName());
                holder.LName_user.setText(model.getLName());
                holder.Email_user.setText(model.getEmail());
                System.out.println(model.getFName());

            }
        };//adapter
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(adapter);



        return view;
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView FName_user;
        TextView LName_user;
        TextView Email_user;


        public UserViewHolder(@NonNull View itemView){

            super(itemView);
            FName_user = itemView.findViewById(R.id.FName_user);
            LName_user = itemView.findViewById(R.id.LName_user);
            Email_user = itemView.findViewById(R.id.Email_user);
        }
    }//UserViewHolder

    public ShowUsers_fragment() {
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