package e.matthew.fitnet;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateProfile extends AppCompatActivity implements View.OnClickListener {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText dobEditText;
    private TextView genderTextView;
    private RadioGroup genderRG;
    private RadioButton genderRB;
    private Button createProfileButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        dobEditText = (EditText) findViewById(R.id.dobEditText);
        genderTextView = (TextView) findViewById(R.id.genderTextView);
        genderRG = (RadioGroup) findViewById(R.id.genderRG);
        createProfileButton = (Button) findViewById(R.id.createProfileBtn);

        createProfileButton.setOnClickListener(this);


    }

    private void createProfile() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        int genderID = genderRG.getCheckedRadioButtonId();
        genderRB = findViewById(genderID);
        String gender = genderRB.getText().toString().trim();

        UserProfile userProfile = new UserProfile(firstName, lastName, dob, gender);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid().setValue())
    }

    @Override
    public void onClick(View view) {
        if (view == createProfileButton) {
            createProfile();
        }
    }
}
