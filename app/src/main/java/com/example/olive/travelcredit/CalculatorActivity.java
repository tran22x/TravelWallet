package com.example.olive.travelcredit;

/**
 * Created by student on 2018. 05. 16..
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CalculatorActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6,
            btn7, btn8, btn9, btnAdd, btnSub, btnDivision,
            btnMul, btn10, btnC, btnEqual;

    EditText input;

    float valueOne, valueTwo;

    boolean mAddition , mSubtract ,mMultiplication ,mDivision ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn10 = (Button) findViewById(R.id.btn10);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDivision = (Button) findViewById(R.id.btnDiv);
        btnC = (Button) findViewById(R.id.btnC);
        btnEqual = (Button) findViewById(R.id.btnEql);
        input = (EditText) findViewById(R.id.tvInput);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"9");
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+"0");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (input == null){
                    input.setText("");
                }else {
                    valueOne = Float.parseFloat(input.getText() + "");
                    mAddition = true;
                    input.setText(null);
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueOne = Float.parseFloat(input.getText() + "");
                mSubtract = true ;
                input.setText(null);
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueOne = Float.parseFloat(input.getText() + "");
                mMultiplication = true ;
                input.setText(null);
            }
        });

        btnDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueOne = Float.parseFloat(input.getText()+"");
                mDivision = true ;
                input.setText(null);
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueTwo = Float.parseFloat(input.getText() + "");

                if (mAddition == true){

                    input.setText(valueOne + valueTwo +"");
                    mAddition=false;
                }


                if (mSubtract == true){
                    input.setText(valueOne - valueTwo +"");
                    mSubtract=false;
                }

                if (mMultiplication == true){
                    input.setText(valueOne * valueTwo +"");
                    mMultiplication=false;
                }

                if (mDivision == true){
                    input.setText(valueOne / valueTwo +"");
                    mDivision=false;
                }
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(input.getText()+".");
            }
        });
    }



}