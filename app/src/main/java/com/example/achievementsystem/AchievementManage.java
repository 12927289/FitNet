package com.example.achievementsystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AchievementManage {
    private static Achievements achievements;

    public static Achievements getAchievements(Context context){
        if(achievements==null){
            achievements=new Achievements(context,"Achievements",null,1);
        }
        return achievements;
    }

    public static void execSQL(SQLiteDatabase db,String sql){
        if(db!=null){
            if(sql!=null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }

    }

    public static Cursor selectDataBySql(SQLiteDatabase db,String sql,
                                         String[] selectedAchievement){
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(sql,selectedAchievement);
        }
        return cursor;
    }

    public static List<AchievementsPrinted> cursorToList(Cursor cursor){
        List<AchievementsPrinted> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex(Constant.name);
            int descriptionIndex = cursor.getColumnIndex(Constant.desc);
            int statusIndex = cursor.getColumnIndex(Constant.status);
            int targetIndex = cursor.getColumnIndex(Constant.target);
            int _id = cursor.getInt(idIndex);
            String Achievement_name = cursor.getString(nameIndex);
            int Achievement_status = cursor.getInt(statusIndex);
            String Achievement_description = cursor.getString(descriptionIndex);
            int Achievement_target = cursor.getInt(targetIndex);
            AchievementsPrinted achievementsPrinted = new AchievementsPrinted(
                    _id, Achievement_name, Achievement_status, Achievement_description, Achievement_target);
            list.add(achievementsPrinted);
        }
        return list;

    }
}
