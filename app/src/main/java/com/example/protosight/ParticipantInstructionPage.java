package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.protosight.adapters.InstructionAdapter;
import com.example.protosight.models.InstructionItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ParticipantInstructionPage extends AppCompatActivity {
    private String testCode;
    private String TAG = "ParticipantInstructionPage";
    private ViewPager viewPager;
    private InstructionAdapter instructionAdapter;
    private TabLayout tabLayout;
    private String testName;
    private Button nextButton;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_participant_instruction_page);
        testCode = getIntent().getStringExtra("testCode");
        testName = getIntent().getStringExtra("testName");
        viewPager = findViewById(R.id.instruction_pager);
        tabLayout = findViewById(R.id.tab_indicator);
        nextButton = findViewById(R.id.btn_next);


        List<InstructionItem> mList = new ArrayList<>();
        mList.add(new InstructionItem("You will be presented with a number of tasks to complete.\n \n After each task, a short questionnaire may be presented.", R.drawable.img1));
        mList.add(new InstructionItem("Once the task begins, your screen and voice will be recorded.\n \n Let us know what you are looking at, doing, and thinking as you interact with the prototype.", R.drawable.img2));
        mList.add(new InstructionItem("You can view the task instructions anytime during your recorded session.", R.drawable.img3));
        mList.add(new InstructionItem("Once you have completed the task, press the exit icon to save and exit the session.", R.drawable.img4));

        instructionAdapter = new InstructionAdapter(this, mList);
        viewPager.setAdapter(instructionAdapter);

        tabLayout.setupWithViewPager(viewPager);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = viewPager.getCurrentItem();
                if (pos < mList.size()){
                    pos ++;
                    viewPager.setCurrentItem(pos);
                }

                if (pos == mList.size() - 1){
                    Log.d(TAG, "LAST");
                    nextButton.setText("Start");
                }

                if (pos == mList.size()){
                    Intent intent = new Intent(ParticipantInstructionPage.this, ParticipantListTasksPage.class);
                    intent.putExtra("testCode", testCode);
                    intent.putExtra("testName", testName);
                    startActivity(intent);
                }

            }
        });

    }


    public void skipTestOnClick(View view){
        Log.d(TAG, "starting");
        Intent intent = new Intent(ParticipantInstructionPage.this, ParticipantListTasksPage.class);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testName", testName);
        startActivity(intent);
    }

}