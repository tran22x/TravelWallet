package com.example.olive.travelcredit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olive.travelcredit.adapter.TransactionAdapter;
import com.example.olive.travelcredit.data.Transaction;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTransactionActivity extends AppCompatActivity {

    private String tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        if (getIntent().getSerializableExtra("tripName") != null) {
            tripName = getIntent().getSerializableExtra("tripName").toString();
        }

        final EditText etTransactionName = findViewById(R.id.etTransactionName);
        final EditText etSender = findViewById(R.id.etSender);
        final EditText etReceiver = findViewById(R.id.etReceiver);
        final EditText etAmount = findViewById(R.id.etAmount);
        final EditText etDetail = findViewById(R.id.etDetail);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((etTransactionName.getText() != null && etSender.getText() != null && etReceiver.getText() != null && etAmount != null)) {
                    Transaction nT = new Transaction(etTransactionName.getText().toString(), etSender.getText().toString(), etReceiver.getText().toString(), Integer.parseInt(etAmount.getText().toString()), etDetail.getText().toString());
                    String key = FirebaseDatabase.getInstance().getReference().child("trips").child(tripName).child("transactions").push().getKey();
                    FirebaseDatabase.getInstance().getReference().child("trips").child(tripName).child("transactions").child(key).setValue(nT);
                    finish();
                }
            }
        });

    }

}
