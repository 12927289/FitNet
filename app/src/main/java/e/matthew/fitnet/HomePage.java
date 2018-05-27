package e.matthew.fitnet;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomePage extends Fragment implements SensorEventListener{

    private TextView stepsTextView;
    private TextView distanceTextView;
    private TextView caloriesTextView;
    private TextView percentageTextView;
    private ProgressBar stepsPB;
    private ProgressBar distancePB;
    private ProgressBar caloriesPB;
    private ProgressBar percentagePB;
    private Button resetBtn;
    private SensorManager sensorManager;
    boolean activityRunning;

    int pastSteps;
    int steps;
    int nowSteps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_page,null);

        stepsTextView = (TextView) v.findViewById(R.id.stepsTextView);
        distanceTextView = (TextView) v.findViewById(R.id.distanceTextView);
        caloriesTextView = (TextView) v.findViewById(R.id.caloriesTextView);
        percentageTextView = (TextView) v.findViewById(R.id.percentageTextView);
        stepsPB = (ProgressBar) v.findViewById(R.id.stepsPB);
        distancePB = (ProgressBar) v.findViewById(R.id.distancePB);
        caloriesPB = (ProgressBar) v.findViewById(R.id.caloriesPB);
        percentagePB = (ProgressBar) v.findViewById(R.id.percentagePB);
        resetBtn = (Button) v.findViewById(R.id.resetBtn);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        stepsPB.setMax(1000);
        distancePB.setMax(1000);
        percentagePB.setMax(1000);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStepZero();
            }
        });

        return v;
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
        stepsTextView.setText(String.valueOf(nowSteps) + " steps");
        distanceTextView.setText(getDistanceByStep((long) nowSteps) + "km");
        caloriesTextView.setText(getCalorieByStep((long) nowSteps) + "cal");
    }

    @Override
    public void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, sensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getActivity(), "Step counter not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        steps = (int) sensorEvent.values[0];
        if(pastSteps < steps) nowSteps = steps - pastSteps;
        if (activityRunning) {
            stepsTextView.setText(String.valueOf(nowSteps) + " steps");
            distanceTextView.setText(getDistanceByStep((long) nowSteps) + "km");
            caloriesTextView.setText(getCalorieByStep((long) nowSteps) + "cal");
            stepsPB.setProgress(nowSteps);
            distancePB.setProgress((int) Math.round(nowSteps*0.6/1000));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
