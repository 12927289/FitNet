package com.example.james.fitnet.com.achievement.shaohang.fitnet;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class StepDatabase extends SQLiteOpenHelper {
    public StepDatabase(Context context,String name,
                                SQLiteDatabase.CursorFactory factory,
                                int version){
        super(context,name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql =  "create table steps(_id Integer primary key, step Integer)";
        db.execSQL(sql);
        String sqls0 = "insert into steps values(0,0)";
        db.execSQL(sqls0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
