package com.example.smiecioltest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;



public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mlayaut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        Toolbar toolbar = findViewById(R.id.toolbar);
        //setActionBar(toolbar);
        //setSupportActionBar(toolbar);

        mlayaut = (DrawerLayout) findViewById(R.id.admin_activity);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mtoggle = new ActionBarDrawerToggle(this,mlayaut, toolbar ,R.string.open,R.string.close);
        mlayaut.addDrawerListener(mtoggle);
        mtoggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,
                    new MyProfile_fragment()).commit();
            navigationView.setCheckedItem(R.id.my_profile);
        }
        //onas_ragment onas_fragmentt = new onas_ragment();
        //FragmentManager fm = getSupportFragmentManager();
        //fm.beginTransaction().add(R.id.Fragment_container, onas_fragmentt);


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.my_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new MyProfile_fragment()).commit();
                break;
            case R.id.statystyki:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new statystyki_fragment()).commit();
                break;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                // getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new LogOut_fragment()).commit();
                break;
            case R.id.product_to_acceptation_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new ShowProductToAcceptation_fragment()).commit();
                break;


            case R.id.show_users:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new ShowUsers_fragment()).commit();
                break;
            case R.id.product_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new Products_fragment()).commit();

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