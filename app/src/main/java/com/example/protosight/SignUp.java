package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void registerOnClick(View view){


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