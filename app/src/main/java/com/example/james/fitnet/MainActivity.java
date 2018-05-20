package com.example.james.fitnet;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView mTextMessage;
    private Button mShareBtn;
    private Button mSetZeroBtn;

    private SensorManager sensorManager;
    private TextView count;
    private TextView distance;
    private TextView calorie;
    boolean activityRunning;

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
    }

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
