package com.example.olive.travelcredit;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by student on 2018. 05. 15..
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olive.travelcredit.data.MoneyResult;
import com.google.gson.Gson;

import org.json.JSONObject;

//import com.example.olive.travecredit.data.MoneyResult;
import com.example.olive.travelcredit.network.MoneyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoneyConverterActivity extends AppCompatActivity {

    private final String URL_BASE =
            "http://api.fixer.io";

    private Button btnGetRates;
    private TextView tvResult;
    private Spinner spFrom;
    private Spinner spTo;
    private EditText etInput;
    private String txtFrom;
    private String txtTo;
    private int inputAmount;
    private int resultAmount;
    private CoordinatorLayout layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_conv);

        layoutContent = (CoordinatorLayout) findViewById(
                R.id.layoutContent);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MoneyAPI moneyAPI = retrofit.create(MoneyAPI.class);

        etInput = findViewById(R.id.etInputAmount);
        tvResult = findViewById(R.id.tvResult);
        btnGetRates = findViewById(R.id.btnRates);
        spFrom = findViewById(R.id.spFromCur);
        spTo = findViewById(R.id.spToCur);
        etInput = findViewById(R.id.etInputAmount);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);


        spFrom.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        txtFrom = spFrom.getSelectedItem().toString();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showSnackBarMessage("NOTHING SELECTED");
                    }
                });

        spTo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        txtTo = spTo.getSelectedItem().toString();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showSnackBarMessage("NOTHING SELECTED");
                    }
                });

        btnGetRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             moneyAPI.getRates(txtFrom).enqueue(new Callback<MoneyResult>() {
                    @Override
                    public void onResponse(Call<MoneyResult> call, Response<MoneyResult> response) {

                        Integer calc = Integer.parseInt(etInput.getText().toString());
                        Double data =  response.body().getRates().gethUF();
                        Long result = (calc* Math.round(data));
                        tvResult.setText(result.toString());
                    }

                    @Override
                    public void onFailure(Call<MoneyResult> call, Throwable t) {
                        Toast.makeText(MoneyConverterActivity.this, "Error: "+
                                t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
}