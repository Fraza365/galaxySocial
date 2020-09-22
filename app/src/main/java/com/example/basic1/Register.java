package com.example.basic1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;

import static android.graphics.Color.colorSpace;
import static android.graphics.Color.rgb;

public class Register extends AppCompatActivity {

    TextView dob, loginLink ;
    DatePickerDialog.OnDateSetListener DateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginLink= findViewById(R.id.loginLink);
        dob = findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Register.this,R.style.Theme_AppCompat_DayNight_Dialog,DateSetListener,year,month,day);
                dialog.show();
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.GRAY);
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);

            }
        });
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + month + "/" + year ;
                dob.setText(date);
            }
        };
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });
    }
}

