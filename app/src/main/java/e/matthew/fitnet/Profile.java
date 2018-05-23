package e.matthew.fitnet;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends Fragment{

    private TextView nameTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView ageTextView;
    private TextView genderTextView;
    private ProgressBar bmiProgressBar;
    private TextView bmiTextView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile,null);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        heightTextView = (TextView) v.findViewById(R.id.heightTextView);
        weightTextView = (TextView) v.findViewById(R.id.weightTextView);
        ageTextView = (TextView) v.findViewById(R.id.ageTextView);
        genderTextView = (TextView) v.findViewById(R.id.genderTextView);
        bmiProgressBar = (ProgressBar) v.findViewById(R.id.bmiProgressBar);
        bmiTextView = (TextView) v.findViewById(R.id.bmiTextView);

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
        return v;
    }
}
