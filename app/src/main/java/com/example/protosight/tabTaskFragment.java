package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class tabTaskFragment extends Fragment {
    private String TAG = "tabTaskFragment";

    public tabTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_task, container, false);
        FloatingActionButton button = view.findViewById(R.id.add_task);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "add task.....");
                Intent intent = new Intent(getActivity(), CreateTest.class);
                startActivity(intent);
            }
        });
        return view;


    }
}