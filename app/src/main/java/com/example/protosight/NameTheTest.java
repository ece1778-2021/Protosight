package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NameTheTest extends AppCompatActivity {

    private String prototypeName;
    private String projectCode;
    private EditText testName;
    private TextView prototypeNameBar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_name);
        testName = findViewById(R.id.Enter_Test_Name);
        prototypeNameBar = findViewById(R.id.Test_Name);
        prototypeName = getIntent().getExtras().getString("prototypeName");
        Log.d("taggg",prototypeName);
        prototypeNameBar.setText(prototypeName);
        projectCode = getIntent().getExtras().getString("projectCode");
    }

    public void NameTheTest(View view) {
        String theTestName = testName.getText().toString();
        if (theTestName.equals("")){
            Toast.makeText(this, "Please fill the test name.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, CreateTask.class);
            intent.putExtra("projectCode", projectCode);
            intent.putExtra("theTestName", theTestName);
            startActivity(intent);
        }
    }
}
