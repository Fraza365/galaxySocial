package com.example.basic1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    Button tbtn,logoutBtn;
    ListView tlist;
    sqlLiteConfig db = new sqlLiteConfig(Testing.this);
    ArrayList<Person> plist = new ArrayList<>();
    Person currentObj;
    int currentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        tuser = findViewById(R.id.tuser);
        tpass = findViewById(R.id.tpass);
        tbtn = findViewById(R.id.tbtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        tlist = findViewById(R.id.tlist);


        if(MainActivity.preferences.getString("username","").isEmpty())
        {
            Intent intent = new Intent(Testing.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.editor.putString("username", "");
                MainActivity.editor.commit();

                Intent intent = new Intent(Testing.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Toast.makeText(Testing.this, "Welcome " + MainActivity.preferences.getString("username",""), Toast.LENGTH_SHORT).show();

        tlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                currentId = plist.get(position).id;
                currentObj = new Person(currentId,plist.get(position).username,plist.get(position).password);
                return false;
            }
        });
        getData();

        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tbtn.getText().toString().matches("Update"))
                {
                    if(db.updateData(currentId,tuser.getText().toString(),tpass.getText().toString()))
                        Toast.makeText(Testing.this, "User Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Testing.this, "User Not Updated", Toast.LENGTH_SHORT).show();

                    getData();
                    tbtn.setText("Insert");
                }
                else{

                }

                tuser.setText("");
                tpass.setText("");

            }
        });

        registerForContextMenu(tlist);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        new MenuInflater(getApplicationContext()).inflate(R.menu.listmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()){
            case "Edit":
                tuser.setText(currentObj.username);
                tpass.setText(currentObj.password);
                tbtn.setText("Update");
                break;
            case "Delete":
                if(db.deleteData(currentId))
                    Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "User Not deleted", Toast.LENGTH_SHORT).show();

                getData();
                break;
        }
        return super.onContextItemSelected(item);
    }


    public void getData(){
        Cursor cursor = db.selectData();
        cursor.moveToFirst();
        ArrayList list = new ArrayList();
        plist.clear();
        list.clear();
        while (!cursor.isAfterLast()){
            plist.add(new Person(cursor.getInt(0) ,cursor.getString(1),cursor.getString(2)));
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
//        ArrayAdapter adapter = new ArrayAdapter(Testing.this,android.R.layout.simple_list_item_1,plist);
        CustomAdapter adapter = new CustomAdapter(Testing.this, plist);
        tlist.setAdapter(adapter);
    }
}
class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList<Person> data;

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
        TextView password = convertView.findViewById(R.id.password);
        Log.d("testData", data.get(position).username);
        username.append(data.get(position).username);
        password.append(data.get(position).password);
        return convertView;
    }
}

class Person {
    int id;
    String username,password;

    public Person(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}