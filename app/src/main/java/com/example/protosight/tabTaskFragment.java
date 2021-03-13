package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.protosight.adapters.ListAllProjectAdapter;
import com.example.protosight.adapters.ListAllTestAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class tabTaskFragment extends Fragment {

    private FirebaseAuth myAuth;
    private String TAG = "tabTaskFragment";
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<Map> testInfo;

    public tabTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        testInfo = new ArrayList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_test, container, false);
        FloatingActionButton button = view.findViewById(R.id.add_task);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "add task.....");
                Intent intent = new Intent(getActivity(), CreateTest.class);
                startActivity(intent);
            }
        });
        db.collection("tests").
                whereEqualTo("creatorEmail", myAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()){
                                testInfo.add(document.getData());
                            }

                            recyclerView = view.findViewById(R.id.test_list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            if (testInfo.size()!=0) {
                                ListAllTestAdapter adapter = new ListAllTestAdapter(tabTaskFragment.this, testInfo);
                                recyclerView.setAdapter(adapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        return view;
    }
}