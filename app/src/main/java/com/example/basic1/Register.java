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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.graphics.Color.colorSpace;
import static android.graphics.Color.rgb;

public class Register extends AppCompatActivity {

    TextView name,username,dob,email,password,loginLink ;
    Button registerBtn;
    DatePickerDialog.OnDateSetListener DateSetListener;
    String gender = "Male";
    sqlLiteConfig db = new sqlLiteConfig(Register.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        dob = findViewById(R.id.dob);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        loginLink= findViewById(R.id.loginLink);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean a = db.insertData(username.getText().toString(),password.getText().toString(),name.getText().toString(),dob.getText().toString(),gender,email.getText().toString());
                if(a){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Register.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    gender="male";
                    break;
            case R.id.female:
                if (checked)
                    gender="female";
                    break;
        }

    }
}

