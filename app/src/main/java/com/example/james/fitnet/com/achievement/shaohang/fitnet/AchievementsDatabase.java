package com.example.james.fitnet.com.achievement.shaohang.fitnet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AchievementsDatabase extends SQLiteOpenHelper{
    public AchievementsDatabase(Context context,String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version){
        super(context,name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table achievements" +
                "(_id Integer primary key,"+DatabaseConstant.name+" text," +
                ""+DatabaseConstant.status+" text,"+DatabaseConstant.desc+" text,"+DatabaseConstant.target+" Integer," +
                ""+DatabaseConstant.obtaincount+" Integer)";
        db.execSQL(sql);

        String sqls0 = "insert into achievements values(0,'First steps', 'undone','take 1 step in one day',1,0)";
        String sqls1 = "insert into achievements values(1,'2 steps', 'undone','take 2 steps in one day',2,0)";
        String sqls2 = "insert into achievements values(2,'5 steps', 'undone','take 5 steps in one day',5,0)";
        String sqls3 = "insert into achievements values(3,'10 steps', 'undone','take 10 steps in one day',10,0)";
        String sqls4 = "insert into achievements values(4,'20 steps', 'undone','take 20 steps in one day',20,0)";
        String sqls5 = "insert into achievements values(5,'100 steps', 'undone','take 100 steps in one day',100,0)";
        String sqls6 = "insert into achievements values(6,'200 steps', 'undone','take 200 steps in one day',200,0)";
        String sqls7 = "insert into achievements values(7,'500 steps', 'undone','take 500 steps in one day',500,0)";
        String sqls8 = "insert into achievements values(8,'1000 steps', 'undone','take 1000 steps in one day',1000,0)";
        String sqls9 = "insert into achievements values(9,'2000 steps', 'undone','take 2000 steps in one day',2000,0)";
        String sqls10 = "insert into achievements values(10,'5000 steps', 'undone','take 5000 steps in one day',5000,0)";

        db.execSQL(sqls0);db.execSQL(sqls1);db.execSQL(sqls2);db.execSQL(sqls3);db.execSQL(sqls4);db.execSQL(sqls5);
        db.execSQL(sqls6);db.execSQL(sqls7);db.execSQL(sqls8);db.execSQL(sqls9);db.execSQL(sqls10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
