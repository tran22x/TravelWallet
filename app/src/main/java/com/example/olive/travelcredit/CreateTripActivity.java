package com.example.olive.travelcredit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olive.travelcredit.data.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        final EditText etTrip = findViewById(R.id.etTrip);

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTrip.getText() != null) {
                    String tripName = etTrip.getText().toString();
                    Trip newTrip = new Trip(tripName, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    FirebaseDatabase.getInstance().getReference().child("trips").child(tripName).setValue(newTrip).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateTripActivity.this, "Trip created", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    etTrip.setError("This field cannot be empty.");
                }
            }
        });
    }
}
