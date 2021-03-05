package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.protosight.adapters.ListAllProjectAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class tabProjectFragment extends Fragment {
    private String TAG = "tabProjectFragment";
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public tabProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_tab_project, container, false);
        FloatingActionButton button = view.findViewById(R.id.add_project);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateProject.class);
                startActivity(intent);
            }
        });


        RecyclerView rv = view.findViewById(R.id.project_list);
//        SwipeMenuListView rv = view.findViewById(R.id.project_list);
        db.collection("prototypes")
                .whereEqualTo("creatorEmail", mAuth.getCurrentUser().getEmail())
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> projectNames = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        projectNames.add(document.getString("name"));
                    }
//                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
//                    rv.setLayoutManager(layoutManager);
                    rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    rv.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
                    ListAllProjectAdapter ia = new ListAllProjectAdapter(projectNames, view.getContext());
                    rv.setAdapter(ia);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });




        return view;

    }



}