package com.example.achievementsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Achievements extends SQLiteOpenHelper{

    public Achievements(Context context,String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version){
        super(context,name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table achievements" +
                "(_id Integer primary key,"+Constant.name+" text," +
                ""+Constant.status+" Integer,"+Constant.desc+" text,"+Constant.target+" Integer)";
        db.execSQL(sql);

        String sqls0 = "insert into achievements values(1,'1 steps', 0,'take 1 step in one day',1)";
        String sqls1 = "insert into achievements values(2,'2 steps', 0,'take 2 steps in one day',2)";
        String sqls2 = "insert into achievements values(3,'5 steps', 0,'take 5 steps in one day',5)";
        String sqls3 = "insert into achievements values(4,'10 steps', 0,'take 10 steps in one day',10)";
        String sqls4 = "insert into achievements values(5,'20 steps', 0,'take 20 steps in one day',20)";
        db.execSQL(sqls0);
        db.execSQL(sqls1);
        db.execSQL(sqls2);
        db.execSQL(sqls3);
        db.execSQL(sqls4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}



