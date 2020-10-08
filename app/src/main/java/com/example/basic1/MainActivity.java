package com.example.basic1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button loginBtn;
    TextView registerLink;
    sqlLiteConfig db;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerLink = findViewById(R.id.registerLink);

        db = new sqlLiteConfig(MainActivity.this);

        preferences = getSharedPreferences("loginState.txt", MODE_PRIVATE);
        editor = preferences.edit();

        if(!preferences.getString("username","").isEmpty())
        {
            Intent intent = new Intent(MainActivity.this, Testing.class);
            startActivity(intent);
            finish();
        }
            loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.Login(username.getText().toString(), password.getText().toString());
                if(cursor != null){

                    editor.putString("username", username.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, Testing.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(MainActivity.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

    }
}
