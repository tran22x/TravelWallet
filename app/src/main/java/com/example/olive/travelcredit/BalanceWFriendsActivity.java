package com.example.olive.travelcredit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.olive.travelcredit.adapter.BalanceWFriendsAdapter;
import com.example.olive.travelcredit.adapter.TransactionAdapter;
import com.example.olive.travelcredit.data.Record;
import com.example.olive.travelcredit.data.RecordList;
import com.example.olive.travelcredit.data.Transaction;

import java.util.List;

/**
 * Created by minhntran on 5/15/18.
 */

public class BalanceWFriendsActivity extends AppCompatActivity {
    private BalanceWFriendsAdapter balanceWFriendsAdapter;
    private List<Record> recordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_friends);

        if (getIntent().getSerializableExtra("recordList") != null) {
            RecordList rec = (RecordList) getIntent().getSerializableExtra("recordList");
            recordList = rec.getRecordList();
        }




        RecyclerView recyclerView = findViewById(R.id.recyclerBalance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        initBalance(recyclerView);

    }

    public void initBalance(final RecyclerView recyclerView) {
        balanceWFriendsAdapter = new BalanceWFriendsAdapter(recordList, BalanceWFriendsActivity.this);
        recyclerView.setAdapter(balanceWFriendsAdapter);

    }


}
