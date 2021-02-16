package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.example.protosight.models.Creator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;



public class SignUp extends AppCompatActivity {

    private EditText usernameText, passwordText, emailText, confirmPasswordText;
    private FirebaseAuth mAuth;
    private String TAG = "SignUpPage";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        emailText = (EditText) findViewById(R.id.email);
        confirmPasswordText = (EditText) findViewById(R.id.confirm_password);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    public void registerOnClick(View view){
        String username, password, confirmPassword, email;
        username = usernameText.getText().toString();
        password = passwordText.getText().toString();
        confirmPassword = confirmPasswordText.getText().toString();
        email = emailText.getText().toString();

        if (validate_input(username, password, confirmPassword, email)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // Save to db and bitmap uir to storage
                                Creator creator = new Creator(
                                        username,
                                        email,
                                        mAuth.getCurrentUser().getUid()
                                );

                                // Direct to login page
                                saveCreatorToFirebase(creator);
                                Log.d(TAG, "createUserWithEmail:success");

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



    private void saveCreatorToFirebase(Creator user){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating user...");
        progressDialog.show();
        // save user to db
        db.collection("creators").document(user.getUid()).set(user.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "createUserToFireStore:success");
                        progressDialog.dismiss();
                        Intent intent = new Intent(SignUp.this, Login.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Fail to upload image profile. Will use the default one",
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "createUserToFireStore:failed");
                    }
                });


    }





    private boolean validate_input(String username, String password, String confirmPassword, String email){
        boolean res = true;
        if (username.isEmpty()){
            Toast.makeText(getApplicationContext(), "Username cannot be blank!",
                    Toast.LENGTH_SHORT).show();
            res = false;
        } else if (!password.equals(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Two password do not match!.",
                    Toast.LENGTH_SHORT).show();
            res = false;
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(), "Email is invalid!",
                    Toast.LENGTH_SHORT).show();
            res = false;
        }
        return  res;
    }
}