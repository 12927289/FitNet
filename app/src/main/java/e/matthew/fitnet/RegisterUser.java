package e.matthew.fitnet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private Button registerBtn;
    private EditText emailText;
    private EditText passwordText;
    private ProgressDialog progressDialog;
    private TextView loginTextView;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        firebaseAuth = FirebaseAuth.getInstance();

        registerBtn = (Button) findViewById(R.id.registerBtn);
        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        progressDialog = new ProgressDialog(this);

        registerBtn.setOnClickListener(this);
        loginTextView.setOnClickListener(this);

    }

    private void registerUser() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterUser.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else if (task.getException()instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(RegisterUser.this, "Email already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterUser.this, "Could not register user", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == registerBtn) {
            registerUser();
        }

        if (view == loginTextView) {
            finish();
            startActivity(new Intent(this, CreateProfile.class));
        }
    }
}

