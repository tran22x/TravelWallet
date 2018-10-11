package com.example.olive.travelcredit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.olive.travelcredit.adapter.TripAdapter;
import com.example.olive.travelcredit.data.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TripAdapter tripAdapter;
    private DrawerLayout drawerLayout;
    private CoordinatorLayout layoutContent;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (CoordinatorLayout) findViewById(
                R.id.layoutContent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //fab should assist in adding trip
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTripActivity.class);
                startActivity(intent);
            }
        });

        tripAdapter = new TripAdapter(this,
                FirebaseAuth.getInstance().getCurrentUser().getUid());

        RecyclerView recyclerViewPlaces = (RecyclerView) findViewById(R.id.recyclerViewTrips);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerViewPlaces.setLayoutManager(layoutManager);
        recyclerViewPlaces.setAdapter(tripAdapter);
//
//        TextView currUser = findViewById(R.id.currUser);
//        currUser.setText("Signed in as: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


        initTrips();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent in = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(in);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_calc:
                        Intent inte = new Intent(MainActivity.this, CalculatorActivity.class);
                        startActivity(inte);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_money_conv:
                        Intent inten = new Intent(MainActivity.this, MoneyConverterActivity.class);
                        startActivity(inten);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_help:
                        showSnackBarMessage(getString(R.string.txt_help));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_about:
                        showSnackBarMessage(getString(R.string.txt_about));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_sign_out:
                        Intent intentt = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intentt);
                        break;
                }
                return false;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent in = new Intent(MainActivity.this, MainActivity.class);
            startActivity(in);
            return true;
        }else if (id == R.id.action_money) {
            Intent inten = new Intent(MainActivity.this, MoneyConverterActivity.class);
            startActivity(inten);
            return true;
        }else if (id == R.id.action_calc) {
            Intent inte = new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(inte);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void showSnackBarMessage(String message) {
        Snackbar.make(layoutContent,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.action_hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }

    private void initTrips() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("trips");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Trip newTrip = dataSnapshot.getValue(Trip.class);
                tripAdapter.addTrip(newTrip, dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                tripAdapter.removeTripByKey(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void startTransactionActivity (String tripName) {


//        Transaction nT = new Transaction("Flixbus", "Maikute", "Minhkute", 10, "");
//        String key = FirebaseDatabase.getInstance().getReference().child("trips").child(tripName).child("transactions").push().getKey();
//        FirebaseDatabase.getInstance().getReference().child("trips").child(tripName).child("transactions").child(key).setValue(nT);
        //String key = FirebaseDatabase.getInstance().getReference().child("transactions").push().getKey();
        //FirebaseDatabase.getInstance().getReference().child("transactions").child(key).setValue(nT);
        //FirebaseDatabase.getInstance().getReference().child("trips").child(tripName).child("transactions").child(key).setValue(key);

        Intent intent = new Intent (MainActivity.this, TransactionsActivity.class);
        intent.putExtra("tripName", tripName);
        startActivity(intent);





    }
}
