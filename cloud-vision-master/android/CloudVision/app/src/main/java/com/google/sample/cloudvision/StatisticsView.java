package com.google.sample.cloudvision;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class StatisticsView extends AppCompatActivity {

    static ReceiptDatabase database;
    static TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statisticsview);
        tv = (TextView) findViewById(R.id.displayMessage);
        Button back_button = (Button) findViewById(R.id.back_button);
        Button date_button = (Button) findViewById(R.id.datetime_button);
        Button location_button = (Button) findViewById(R.id.location_button);
        try {
            StatisticsView.database = new ReceiptDatabase(getExternalFilesDir("OCR_Tracker_App_Test").getPath() + "/transactions.ser");
        }
        catch (IOException ex) {}  // null pointer exception later

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(StatisticsView.this, MainActivity.class);
                StatisticsView.this.startActivity(myIntent);
            }
        });

        date_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        location_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                // print out list of things bought at location to textview
            }
        });
    }
}