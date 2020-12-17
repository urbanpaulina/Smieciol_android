package com.example.smiecioltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.smiecioltest.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mlayaut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        //setActionBar(toolbar);
       //setSupportActionBar(toolbar);

        mlayaut = (DrawerLayout) findViewById(R.id.main_activity);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mtoggle = new ActionBarDrawerToggle(this,mlayaut, toolbar ,R.string.open,R.string.close);
        mlayaut.addDrawerListener(mtoggle);
        mtoggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,
                    new statystyki_fragment()).commit();
            navigationView.setCheckedItem(R.id.statystyki);
        }
        onas_ragment onas_fragmentt = new onas_ragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.Fragment_container, onas_fragmentt);


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.statystyki:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new statystyki_fragment()).commit();
                break;
            case R.id.oNas:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new onas_ragment()).commit();
                break;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
               // getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new LogOut_fragment()).commit();
                break;
//            case R.id.kontakt:
//                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new MapsActivity()).commit();
//                break;
//            case R.id.bilety:
//                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new bilety_fragment()).commit();
//                break;
//            case R.id.galeria:
//                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new galeria_fragment()).commit();
//                break;
        }
        mlayaut.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed(){
        if(mlayaut.isDrawerOpen(GravityCompat.START)){
            mlayaut.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
//    public void logout ( View view){
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(), Login.class));
//        finish();
//    }
}