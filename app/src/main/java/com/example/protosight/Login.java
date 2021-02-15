package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.protosight.models.Creator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Log.d(TAG, "Curruent Logged in User: " + mAuth.getCurrentUser().getEmail());
            Intent intent = new Intent(this, CreatorLandingPage.class);
            startActivity(intent);
        }
    }

    public void signInOnClick(View view){
        EditText email = (EditText) findViewById(R.id.email);
        EditText pwd = (EditText) findViewById(R.id.password);

        if (validate_input(email.getText().toString(), pwd.getText().toString())) {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Intent intent = new Intent(Login.this, CreatorLandingPage.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                                Toast.makeText(Login.this, "Invalid email or password!",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
    }

    public void signUpOnClick(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }


    private boolean validate_input(String email, String password){
        boolean res = true;

        if (email.isEmpty()){
            res = false;
            Toast.makeText(this, "Email is empty",
                    Toast.LENGTH_LONG).show();
        } else if (password.isEmpty()){
            res = false;
            Toast.makeText(this, "Password is empty",
                    Toast.LENGTH_LONG).show();
        }

        return res;
    }
}