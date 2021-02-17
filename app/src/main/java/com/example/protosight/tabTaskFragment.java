package com.example.protosight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tabTaskFragment#newInstance} factory method to
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
        LinearLayout linearLayout = view.findViewById(R.id.add_task);
        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "add task.....");
            }
        });
        return view;


    }
}