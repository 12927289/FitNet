package com.example.james.fitnet;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.james.fitnet.com.achievement.shaohang.fitnet.AchievementManage;
import com.example.james.fitnet.com.achievement.shaohang.fitnet.AchievementsDatabase;
import com.example.james.fitnet.com.achievement.shaohang.fitnet.AchievementsPrinted;
import com.example.james.fitnet.com.achievement.shaohang.fitnet.StepDatabase;
import com.example.james.fitnet.com.achievement.shaohang.fitnet.StepManage;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView mTextMessage;
    private Button mShareBtn;
    private Button mSetZeroBtn;

    private SensorManager sensorManager;
    private TextView count;
    private TextView distance;
    private TextView calorie;
    boolean activityRunning;
    private AchievementsDatabase achievements;
    private AchievementManage achievementManage = new AchievementManage();
    Handler handler = new Handler();
    int pastSteps;
    int steps;
    int nowSteps;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        achievements =AchievementManage.getAchievements(this);
        SQLiteDatabase db = achievements.getWritableDatabase();
        db.close();



        count = (TextView) findViewById(R.id.count);
        distance = (TextView) findViewById(R.id.kmTv);
        calorie = (TextView) findViewById(R.id.calorieTv);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mShareBtn = (Button)findViewById(R.id.shareBtn);
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "FitNet";
                String shareSub = "I have been walked "+count.getText()+" via FitNet!";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });



        mSetZeroBtn = (Button) findViewById(R.id.setZeroBtn);
        mSetZeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStepZero();
            }
        });



        steps = simulateStep();
        achievementManage.earnAchievement(steps);

        checkAchievements.run();
    }


    public int simulateStep(){
        return 5000;
    }

    public void showAchievements(View view){
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(MainActivity.this);
        //List<AchievementsPrinted> list= achievementManage.getAchievementsList();
        String[] achievement_name = {
                achievementManage.printAchievements(0),
                achievementManage.printAchievements(1),
                achievementManage.printAchievements(2),
                achievementManage.printAchievements(3),
                achievementManage.printAchievements(4),
                achievementManage.printAchievements(5),
                achievementManage.printAchievements(6),
                achievementManage.printAchievements(7),
                achievementManage.printAchievements(8),
                achievementManage.printAchievements(9),
                achievementManage.printAchievements(10)
        };
        /*for(int i =0; i<=list.size();i++){
            achievement_name[i]=list.get(i).toString();
        }
        final String[] achievements = achievement_name;*/
        listDialog.setTitle("Achievements List");
        listDialog.setItems(achievement_name, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,
                        achievementManage.printAchievements(which),
                        Toast.LENGTH_LONG).show();
            }
        });
        listDialog.show();
    }

    public void earnAnAchievementNotify(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("FitNet: You got an achievement!")
                .setContentText("")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setWhen(System.currentTimeMillis())
                .setTicker("")
                .setDefaults(Notification.DEFAULT_SOUND);
        notificationManager.notify(10, mBuilder.build());
    }

    Runnable checkAchievements = new Runnable() {
        @Override
        public void run() {
            if(achievementManage.earnAchievement(steps))
                earnAnAchievementNotify();
            handler.postDelayed(this,2000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, sensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        steps = (int) sensorEvent.values[0];
        if(pastSteps < steps) nowSteps = steps - pastSteps;
        if (activityRunning) {
            count.setText(String.valueOf(nowSteps) + " steps");
            distance.setText("Distance: " + getDistanceByStep((long) nowSteps) + "km");
            calorie.setText("Calorie: " + getCalorieByStep((long) nowSteps) + "cal");
        }
    }

    private String getDistanceByStep(long steps) {
        return String.format("%.2f", steps * 0.6f / 1000);
    }

    private String getCalorieByStep(long steps) {
        return String.format("%.1f", steps * 0.6f * 60 * 1.036f / 1000);
    }

    private void setStepZero() {
        pastSteps = steps;
        nowSteps = 0;
        count.setText(String.valueOf(nowSteps) + " steps");
        distance.setText("Distance: " + getDistanceByStep((long) nowSteps) + "km");
        calorie.setText("Calorie: " + getCalorieByStep((long) nowSteps) + "cal");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
