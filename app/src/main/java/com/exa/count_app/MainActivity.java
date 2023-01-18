package com.exa.count_app;

import static android.text.InputType.TYPE_CLASS_NUMBER;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exa.count_app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private static int num = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        text_change(num);

        TextView textView = findViewById(R.id.tv_Num);
        textView.setOnClickListener(this::text_ope);

        Button btn_plus = findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(this::plus_one);

        Button btn_minus = findViewById(R.id.btn_minus);
        btn_minus.setOnClickListener(this::minus_one);

        Button btn_Reset = findViewById(R.id.btn_Reset);
        btn_Reset.setOnClickListener(this::reset_num);

        Button btn_Th_up = findViewById(R.id.btn_Th_up);
        btn_Th_up.setOnClickListener(this::change_Th_plus);
        Button btn_Th_down = findViewById(R.id.btn_Th_down);
        btn_Th_down.setOnClickListener(this::change_Th_minus);
        Button btn_Ha_up = findViewById(R.id.btn_Ha_up);
        btn_Ha_up.setOnClickListener(this::change_Ha_plus);
        Button btn_Ha_down = findViewById(R.id.btn_Ha_down);
        btn_Ha_down.setOnClickListener(this::change_Ha_minus);
        Button btn_Te_up = findViewById(R.id.btn_Te_up);
        btn_Te_up.setOnClickListener(this::change_Te_plus);
        Button btn_TE_down = findViewById(R.id.btn_TE_down);
        btn_TE_down.setOnClickListener(this::change_Te_minus);
        Button btn_One_up = findViewById(R.id.btn_One_up);
        btn_One_up.setOnClickListener(this::change_On_plus);
        Button btn_One_down = findViewById(R.id.btn_One_down);
        btn_One_down.setOnClickListener(this::change_On_minus);
    }

    public void text_change(int number) { //text表示
        String number_s = String.format("%04d",number);
        TextView tv_main1 = (TextView)findViewById(R.id.tv_Count_Num1);
        TextView tv_main2 = (TextView)findViewById(R.id.tv_Count_Num2);
        TextView tv_main3 = (TextView)findViewById(R.id.tv_Count_Num3);
        TextView tv_main4 = (TextView)findViewById(R.id.tv_Count_Num4);
        TextView tv_sub = (TextView)findViewById(R.id.tv_Num);
        TextView[] li = {tv_main1,tv_main2,tv_main3,tv_main4};
        for (int i = 0;i < 4;i++) {
            li[i].setText(String.valueOf(number_s.charAt(i)));
        }
        tv_sub.setText(String.valueOf(number));
    }

    public void text_ope (View v) {
        final EditText editText = new EditText(this);
        editText.setHint(String.valueOf(num));
        editText.setInputType(TYPE_CLASS_NUMBER);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(9);
        editText.setFilters(filters);
        AlertDialog alertdialog = new AlertDialog.Builder(MainActivity.this)
                .setView(editText)
                .setPositiveButton(
                        "Change",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String input_str = String.valueOf(editText.getText());
                                if (input_str.length() == 0) {
                                } else if (Integer.parseInt(input_str) >= 10000 || Integer.parseInt(input_str) < 0) {
                                    final Snackbar snackbar = Snackbar.make(v, "The number is out of range", Snackbar.LENGTH_SHORT);
                                    snackbar.setBackgroundTint(Color.BLACK);
                                    snackbar.setTextColor(Color.WHITE);
                                    snackbar.show();
                                } else {
                                num = Integer.parseInt(input_str);
                                text_change(num);
                                }
                            }
                        })
                .setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .create();
        alertdialog.show();
        Button positiveButton = alertdialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#2478B7"));
        Button negativeButton = alertdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#2478B7"));
    }

    public void plus_one (View v) { //+1
        num += 1;
        if (num >= 10000) {
            num = 0;
        }
        text_change(num);
    }

    public void minus_one (View v) { //-1
        num -= 1;
        if (num < 0) {
            num = 9999;
        }
        text_change(num);
    }

    public void reset_num (View v) {//reset
        new AlertDialog.Builder(MainActivity.this)
        .setTitle("Reset?")
                .setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                num = 0;
                                text_change(num);
                            }
                        })
                .setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .show();
    }

    public void change_Th_plus (View v) { //千の位+1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num1);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("9")) {
            num -= 9000;
        } else {
            num += 1000;
        }
        text_change(num);
    }

    public void change_Th_minus (View v) { //千の位-1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num1);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("0")) {
            num += 9000;
        } else {
            num -= 1000;
        }
        text_change(num);
    }

    public void change_Ha_plus (View v) { //百の位+1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num2);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("9")) {
            num -= 900;
        } else {
            num += 100;
        }
        text_change(num);
    }

    public void change_Ha_minus (View v) { //百の位-1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num2);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("0")) {
            num += 900;
        } else {
            num -= 100;
        }
        text_change(num);
    }

    public void change_Te_plus (View v) { //十の位+1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num3);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("9")) {
            num -= 90;
        } else {
            num += 10;
        }
        text_change(num);
    }

    public void change_Te_minus (View v) { //十の位-1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num3);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("0")) {
            num += 90;
        } else {
            num -= 10;
        }
        text_change(num);
    }

    public void change_On_plus (View v) { //一の位+1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num4);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("9")) {
            num -= 9;
        } else {
            num += 1;
        }
        text_change(num);
    }

    public void change_On_minus (View v) { //一の位-1
        TextView textView = (TextView)findViewById(R.id.tv_Count_Num4);
        String tv_str = textView.getText().toString();
        if (tv_str.equals("0")) {
            num += 9;
        } else {
            num -= 1;
        }
        text_change(num);
    }
}