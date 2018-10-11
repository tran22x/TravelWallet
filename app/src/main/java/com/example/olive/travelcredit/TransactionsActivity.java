package com.example.olive.travelcredit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.olive.travelcredit.adapter.TransactionAdapter;
import com.example.olive.travelcredit.data.Record;
import com.example.olive.travelcredit.data.RecordList;
import com.example.olive.travelcredit.data.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsActivity extends AppCompatActivity {

    private TransactionAdapter transactionAdapter;
    private String tripName;
    public TextView balance;
    public Button btnViewBalUser;

    private List<String> friends = new ArrayList<>();


    private HashMap<String, Integer> moneyRecord = new HashMap<>();
    public int currentBalance = 0;

    public List<Record> recordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_2);

        if (getIntent().getStringExtra("tripName") != null) {
            this.tripName = getIntent().getStringExtra("tripName").toString();
        }

        balance = findViewById(R.id.balance);
        transactionAdapter = new TransactionAdapter(TransactionsActivity.this,
                FirebaseAuth.getInstance().getCurrentUser().getUid(), tripName);
        setUpRecyclerView();
        setUpToolbar();
        setUpFab();
        btnViewBalUser = findViewById(R.id.btnViewBalUser);
        btnViewBalUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateRecordList();
                RecordList rec = new RecordList(recordList);
                Intent intent = new Intent(TransactionsActivity.this, BalanceWFriendsActivity.class);
                intent.putExtra("recordList", rec);
                startActivity(intent);
            }
        });

        initTransactions();
    }

    private void setUpFab() {
        FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.btnTransaction);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionsActivity.this, CreateTransactionActivity.class);
                intent.putExtra("tripName", tripName);
                startActivity(intent);
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);
        ctl.setTitle(tripName);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerViewTransactions = (RecyclerView) findViewById(
                R.id.recyclerViewTransactions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerViewTransactions.setLayoutManager(layoutManager);
        recyclerViewTransactions.setAdapter(transactionAdapter);
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
            Intent in = new Intent(TransactionsActivity.this, MainActivity.class);
            startActivity(in);
            finish();
            return true;
        }else if (id == R.id.action_money) {
            Intent inten = new Intent(TransactionsActivity.this, MoneyConverterActivity.class);
            startActivity(inten);
            finish();
            return true;
        }else if (id == R.id.action_calc) {
            Intent inte = new Intent(TransactionsActivity.this, CalculatorActivity.class);
            startActivity(inte);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTransactions() {
        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("trips").child(tripName).child("transactions");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Transaction newTransaction = dataSnapshot.getValue(Transaction.class);
                if (newTransaction.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                    transactionAdapter.addTransaction(newTransaction, dataSnapshot.getKey());
                }
                else if (newTransaction.getReceiverId().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                    int amount = newTransaction.getAmount();
                    newTransaction.setAmount(amount*(-1));
                    transactionAdapter.addTransaction(newTransaction, dataSnapshot.getKey());
                }

                if (newTransaction.getReceiverId().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                    recordMoney(newTransaction.getSenderId(), newTransaction.getAmount());
                }

                else if (newTransaction.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                    recordMoney(newTransaction.getReceiverId(), newTransaction.getAmount());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                transactionAdapter.removeTransByKey(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<Record> getRecordList() {return recordList;}
    public HashMap<String, Integer> getMoneyRecord() {
        return moneyRecord;
    }

    public void recordMoney (String receiver, int amount) {
        if (moneyRecord.containsKey(receiver)) {
            int balance = moneyRecord.get(receiver);
            moneyRecord.put(receiver, balance + amount);
        }
        else {
            moneyRecord.put(receiver, amount);
            friends.add(receiver);
        }
        currentBalance = currentBalance + amount;
        setBalance(currentBalance);
    }


    public void populateRecordList() {
        if(!recordList.isEmpty()) {
            recordList.clear();
        }
        for (Map.Entry<String, Integer> entry : moneyRecord.entrySet()) {
            String name = entry.getKey();
            Integer amount = entry.getValue();
            recordList.add(new Record(name, amount));
        }
    }

    public void updateRecord(Transaction t) {
        int transAmt = t.getAmount();
        int prevBalance;
        if (t.getReceiverId().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
            prevBalance = moneyRecord.get(t.getSenderId());
            moneyRecord.put(t.getSenderId(), prevBalance - transAmt);
        }
        else {
            prevBalance = moneyRecord.get(t.getReceiverId());
            moneyRecord.put(t.getReceiverId(), prevBalance - transAmt);
        }
        currentBalance = currentBalance - transAmt;
        setBalance(currentBalance);
    }

    public void setBalance (int b) {
        balance.setText("Current balance: " + b + "$");
    }

}
