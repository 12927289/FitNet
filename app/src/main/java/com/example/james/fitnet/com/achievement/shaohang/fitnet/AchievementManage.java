package com.example.james.fitnet.com.achievement.shaohang.fitnet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;


import com.example.james.fitnet.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_LONG;


public class AchievementManage {
    private static AchievementsDatabase achievements;

    public static AchievementsDatabase getAchievements(Context context){
        if(achievements==null){
            achievements=new AchievementsDatabase(context,"Achievements",null,1);
        }
        return achievements;
    }

    public static void execSQL(SQLiteDatabase db, String sql){
        if(db!=null){
            if(sql!=null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }

    public static Cursor selectDataBySql(SQLiteDatabase db, String sql,
                                         String[] selectedAchievement){
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(sql,selectedAchievement);
        }
        return cursor;
    }


    public String printAchievements(int id){
        SQLiteDatabase db = achievements.getWritableDatabase();
        String printSql="select * from achievements";
        Cursor cursor = AchievementManage.selectDataBySql(db,printSql,null);
        List<AchievementsPrinted> list = AchievementManage.cursorToList(cursor);
        AchievementsPrinted achievement = list.get(id);
        db.close();
        return achievement.getAchievement_description()+" : "+achievement.getAchievement_status();
    }

    public static List<AchievementsPrinted> cursorToList(Cursor cursor){
        List<AchievementsPrinted> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex(DatabaseConstant.name);
            int descriptionIndex = cursor.getColumnIndex(DatabaseConstant.desc);
            int statusIndex = cursor.getColumnIndex(DatabaseConstant.status);
            int targetIndex = cursor.getColumnIndex(DatabaseConstant.target);
            int _id = cursor.getInt(idIndex);
            int obtainIndex = cursor.getColumnIndex(DatabaseConstant.obtaincount);
            String Achievement_name = cursor.getString(nameIndex);
            String Achievement_status = cursor.getString(statusIndex);
            String Achievement_description = cursor.getString(descriptionIndex);
            int Achievement_target = cursor.getInt(targetIndex);
            int Achievement_obtaincount = cursor.getInt(obtainIndex);
            AchievementsPrinted achievementsPrinted = new AchievementsPrinted(
                    _id, Achievement_name, Achievement_status, Achievement_description, Achievement_target, Achievement_obtaincount);
            list.add(achievementsPrinted);
        }
        return list;
    }

    public boolean earnAchievement(int step){
        SQLiteDatabase db = achievements.getWritableDatabase();
        String printSql="select * from achievements";
        String updateSql;
        Cursor cursor = AchievementManage.selectDataBySql(db,printSql,null);
        List<AchievementsPrinted> list = AchievementManage.cursorToList(cursor);
        for(AchievementsPrinted achievement : list){
            if(achievement.getAchievement_target()<=step){
                if(achievement.getAchievement_status().equals("undone")){
                updateSql ="update achievements set "+DatabaseConstant.status+"='done' where _id="+achievement.get_id();
                AchievementManage.execSQL(db,updateSql);
                    return true;
                }
                else{
                    int i = achievement.setAchievement_obtaincount();
                    updateSql ="update achievements set "+DatabaseConstant.obtaincount+"="+i+" where _id="+achievement.get_id();
                    AchievementManage.execSQL(db,updateSql);
                }
            }
        }
        db.close();
        return false;
    }
}
