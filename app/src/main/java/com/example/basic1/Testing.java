package com.example.basic1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Testing extends AppCompatActivity {

    EditText tuser,tpass;
    Button tbtn;
    ListView tlist;
    sqlLiteConfig db = new sqlLiteConfig(Testing.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        tuser = findViewById(R.id.tuser);
        tpass = findViewById(R.id.tpass);
        tbtn = findViewById(R.id.tbtn);
        tlist = findViewById(R.id.tlist);


        getData();

        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean success = db.insertData(tuser.getText().toString(), tpass.getText().toString());
                if(success){
                    Toast.makeText(Testing.this, "User Created", Toast.LENGTH_SHORT).show();
                    getData();
                }
                else{
                    Toast.makeText(Testing.this, "User Created Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getData(){
        Cursor cursor = db.selectData();
        cursor.moveToFirst();
        ArrayList list = new ArrayList();
        while (!cursor.isAfterLast()){
            list.add("id : " + cursor.getInt(0) + ", username : " + cursor.getString(1)+ ", password : " + cursor.getString(2));
            cursor.moveToNext();
        }
        ArrayAdapter adapter = new ArrayAdapter(Testing.this,android.R.layout.simple_list_item_1,list);
        tlist.setAdapter(adapter);
    }
}
