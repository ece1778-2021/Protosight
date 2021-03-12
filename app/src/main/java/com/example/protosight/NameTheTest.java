package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NameTheTest extends AppCompatActivity {

    private String prototypeName;
    private String projectCode;
    private EditText testName;
    private TextView prototypeNameBar;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_name);
        testName = findViewById(R.id.Enter_Test_Name);
        prototypeNameBar = findViewById(R.id.Test_Name);
        prototypeName = getIntent().getExtras().getString("prototypeName");
        prototypeNameBar.setText(" "+prototypeName);
        projectCode = getIntent().getExtras().getString("projectCode");
        db = FirebaseFirestore.getInstance();
    }

    public void NameTheTest(View view) {
        String theTestName = testName.getText().toString();
        String creatorEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String testID = UUID.randomUUID().toString().substring(0,8);
        if (theTestName.equals("")){
            Toast.makeText(this, "Please fill the test name.", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> uploadMap = new HashMap<String, String>();
            uploadMap.put("testID" ,testID);
            uploadMap.put("testName", theTestName);
            uploadMap.put("creatorEmail", creatorEmail);
            uploadMap.put("whetherAllowChange", "0");
            db.collection("tests").
                    document(testID).
                    set(uploadMap).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Test_creation", "The test has been successfully stored.");
                        }
                    });
            Intent intent = new Intent(this, CreateTask.class);
            intent.putExtra("projectCode", projectCode);
            intent.putExtra("theTestName", theTestName);
            intent.putExtra("lastActivity", "NameTheTest");
            intent.putExtra("testID",testID);
            startActivity(intent);
        }
    }
}
