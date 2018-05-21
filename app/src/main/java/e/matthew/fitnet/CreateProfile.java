package e.matthew.fitnet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProfile extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText heightEditText;
    private TextView weightEditText;
    private RadioGroup genderRG;
    private RadioButton genderRB;
    private Button createProfileButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        heightEditText = (EditText)findViewById(R.id.heightEditText);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        genderRG = (RadioGroup) findViewById(R.id.genderRG);
        createProfileButton = (Button) findViewById(R.id.createProfileBtn);

        createProfileButton.setOnClickListener(this);

    }

    private void createProfile() {
        String name = nameEditText.getText().toString().trim();
        int age = Integer.parseInt(ageEditText.getText().toString().trim());
        int height = Integer.parseInt(heightEditText.getText().toString().trim());
        int weight = Integer.parseInt(weightEditText.getText().toString().trim());
        int genderID = genderRG.getCheckedRadioButtonId();
        genderRB = findViewById(genderID);
        String gender = genderRB.getText().toString().trim();

        UserProfile userProfile = new UserProfile(name, age, height, weight, gender);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userProfile);
        Toast.makeText(this, "User Profile Created", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, Profile.class));
    }

    @Override
    public void onClick(View view) {
        if (view == createProfileButton) {
            createProfile();
        }
    }
}
