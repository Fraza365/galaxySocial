package com.example.basic1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
//            list.add("id : " + cursor.getInt(0) + ", username : " + cursor.getString(1)+ ", password : " + cursor.getString(2));
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
//        ArrayAdapter adapter = new ArrayAdapter(Testing.this,android.R.layout.simple_list_item_1,list);
        CustomAdapter adapter = new CustomAdapter(Testing.this, list);
        tlist.setAdapter(adapter);
    }
}
class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList data;

    public CustomAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.testinglistitem, null);
        TextView username = convertView.findViewById(R.id.username);
        username.setText(data.get(position).toString());

        return convertView;
    }
}
