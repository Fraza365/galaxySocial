package com.example.basic1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqlLiteConfig extends SQLiteOpenHelper{
    public sqlLiteConfig(@Nullable Context context) {
        super(context, "galaxydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id Integer Primary Key, username varchar(50), password varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insertData(String userName, String password) {
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("insert into users(id, username, password) values (null, '"+userName+"', '"+ password+"')");
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public Cursor selectData(){
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users",null);
            return cursor;
        }
        catch (Exception ex){

            return null;
        }

    }

}
