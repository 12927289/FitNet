package com.example.achievementsystem;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Achievements achievements;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        achievements = AchievementManage.getAchievements(this);
        createAchievementsDB();
    }

    private void createAchievementsDB(){
        SQLiteDatabase db = achievements.getWritableDatabase();
        db.close();
    }

    private String printAchievements(int id){

        SQLiteDatabase db = achievements.getWritableDatabase();
        String printSql="select * from achievements";
        Cursor cursor = AchievementManage.selectDataBySql(db,printSql,null);
            List<AchievementsPrinted> list = AchievementManage.cursorToList(cursor);
        AchievementsPrinted achievement = list.get(id);
        db.close();
        return achievement.getAchievement_description()+" : "+achievement.getAchievement_status()+" : "+achievement.getAchievement_target();
    }  //store the data in the list

    public void showDialog(View view) {
        int id=1;
        switch(view.getId()){
            case R.id.b1:id=0;break;
            case R.id.b2:id=1;break;
            case R.id.b3:id=2;break;
            case R.id.b4:id=3;break;
            case R.id.b5:id=4;break;
        }
        AlertDialog.Builder dialogue=new AlertDialog.Builder(this);
        dialogue.setMessage(printAchievements(id));
        dialogue.setNegativeButton("OK", click);
        AlertDialog alertdialog1=dialogue.create();
        alertdialog1.show();

    }  //print data by click button

    private DialogInterface.OnClickListener click=new DialogInterface.OnClickListener()
    {@Override

    public void onClick(DialogInterface arg0,int arg1) {
        arg0.cancel();
    }
    };

    private void earnAchievement(int step,View view){
        SQLiteDatabase db = achievements.getWritableDatabase();
        String printSql="select * from achievements";
        String updateSql;
        Cursor cursor = AchievementManage.selectDataBySql(db,printSql,null);
        List<AchievementsPrinted> list = AchievementManage.cursorToList(cursor);
        for(AchievementsPrinted achievement : list){
            if(achievement.getAchievement_target()<=step&&achievement.getAchievement_status()==0){
                updateSql ="update achievements set "+Constant.status+"=1 where _id="+achievement.get_id();
                showNote(view,achievement.getAchievement_name());
                AchievementManage.execSQL(db,updateSql);

            }
        }
        db.close();
    }



    public void simulateStep(View view){

        earnAchievement(2,view);
    }

    public void showNote(View view,String name){
        NoteOfEarning.snackbar(view,"Achievement ' "+name+" ' got!").show();
    }
}






