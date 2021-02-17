package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tabProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tabProjectFragment extends Fragment {
    private String TAG = "tabProjectFragment";

    public tabProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_project, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.add_project);
        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateProject.class);
                startActivity(intent);
            }
        });
        return view;

    }



}