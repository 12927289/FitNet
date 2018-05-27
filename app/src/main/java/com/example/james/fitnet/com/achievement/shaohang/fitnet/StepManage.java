package com.example.james.fitnet.com.achievement.shaohang.fitnet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.james.fitnet.com.achievement.shaohang.fitnet.AchievementManage.selectDataBySql;

public class StepManage {
    private static StepDatabase stepDatabase;

    public static StepDatabase getStepDatabase(Context context){
        if(stepDatabase==null){
            stepDatabase=new StepDatabase(context,"Steps",null,1);
        }
        return stepDatabase;
    }

    public int getStep(SQLiteDatabase db){
        String readStep="select * from steps";
        int step = 0;
        Cursor cursor = selectDataBySql(db,readStep,null);
        while(cursor.moveToNext()){
            int stepIndex = cursor.getColumnIndex("step");
            step = cursor.getInt(stepIndex);
        }
        db.close();
        return step;
    }

    public void setStep(int step){
        SQLiteDatabase db = stepDatabase.getWritableDatabase();
        String updateSql ="update steps set step=10 where _id=0";
        StepManage.execSQL(db,updateSql);
        db.close();
    }

    public static void execSQL(SQLiteDatabase db, String sql){
        if(db!=null){
            if(sql!=null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }
}


