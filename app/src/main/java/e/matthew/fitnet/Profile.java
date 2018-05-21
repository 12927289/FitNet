package e.matthew.fitnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private TextView nameTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView ageTextView;
    private TextView genderTextView;
    private Button logoutBtn;
    private ProgressBar bmiProgressBar;
    private TextView bmiTextView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        weightTextView = (TextView) findViewById(R.id.weightTextView);
        ageTextView = (TextView) findViewById(R.id.ageTextView);
        genderTextView = (TextView) findViewById(R.id.genderTextView);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        bmiProgressBar = (ProgressBar) findViewById(R.id.bmiProgressBar);
        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        logoutBtn.setOnClickListener(this);

        DatabaseReference ref = databaseReference.getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                UserProfile userProfile = dataSnapshot.child(user.getUid()).getValue(UserProfile.class);

                String name = userProfile.getName();
                int height = userProfile.getHeight();
                int weight = userProfile.getWeight();
                int age = userProfile.getAge();
                String gender = userProfile.getGender();

                nameTextView.setText(name);
                ageTextView.setText("Age: " + String.valueOf(age));
                heightTextView.setText("Height: " + String.valueOf(height) + " cm");
                weightTextView.setText("Weight: " + String.valueOf(weight) + " kg");
                genderTextView.setText("Gender: " + gender);

                double bmi = (weight / (height * 0.01))/(height * 0.01);
                bmi = Math.round(bmi*10.0)/10;
                bmiProgressBar.setProgress((int)Math.round(bmi));
                if (bmi < 18.5)
                    bmiTextView.setText("Your BMI is " + String.valueOf(bmi) + ", You're Underweight");
                if (bmi >= 18.5 && bmi < 25)
                    bmiTextView.setText("Your BMI is " + String.valueOf(bmi) + ", You're Normal Weight");
                if (bmi >= 25 && bmi < 30)
                    bmiTextView.setText("Your BMI is " + String.valueOf(bmi) + ", You're Overweight");
                if (bmi >= 30)
                    bmiTextView.setText("Your BMI is " + String.valueOf(bmi) + ", You're Obese");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == logoutBtn) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }
}
