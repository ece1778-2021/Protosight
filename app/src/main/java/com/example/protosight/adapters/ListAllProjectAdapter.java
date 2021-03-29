package com.example.protosight.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.protosight.PlayPrototype;
import com.example.protosight.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListAllProjectAdapter extends RecyclerView.Adapter<ListAllProjectAdapter.ProjectViewHolder>  {
    public ArrayList<String> projects;
    private ArrayList<String> projectIDs;
    private ArrayList<String> firstImages;
    private Context context;
    private String TAG = "CreateProjectImageAdapter";
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private RecyclerView rv;
    private ListAllProjectAdapter listAllProjectAdapter;
    private FirebaseAuth mAuth;


    public ListAllProjectAdapter(ArrayList<String> projects, Context context, ArrayList<String> projectIDs, ArrayList<String> firstImages, RecyclerView rv) {

        this.context = context;
        this.projectIDs = projectIDs;
        this.projects = projects;
        this.firstImages = firstImages;
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        this.rv = rv;
        listAllProjectAdapter = this;
        mAuth = FirebaseAuth.getInstance();


    }

    @NonNull
    @Override
    public ListAllProjectAdapter.ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_project_view, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAllProjectAdapter.ProjectViewHolder holder, int position) {
        holder.currentProjectName.setText(this.projects.get(position));

        holder.playPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Play project : " + projects.get(position));
                Intent intent = new Intent(context, PlayPrototype.class);
                intent.putExtra("projectID", projectIDs.get(position));
                intent.putExtra("firstImageRef", firstImages.get(position));
                context.startActivity(intent);
            }
        });

        holder.editPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Play project : " + projects.get(position));
                db.collection("prototypes").
                        whereEqualTo("projectCode", projectIDs.get(position)).
                        get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot d : task.getResult()){
                                    db.collection("prototypes").document(d.getId()).delete();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                db.collection("hotspots").
                        whereEqualTo("projectID", projectIDs.get(position)).
                        get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                ArraySet<String> p = new ArraySet<>();
                                for (QueryDocumentSnapshot d : task.getResult()){
                                    db.collection("hotspots").document(d.getId()).delete();
                                    p.add(d.getString("screenshotFrom"));
                                    p.add(d.getString("screenshotTo"));
                                }
                                for (String path : p){
                                    removeImage(path);
                                }


                                projectIDs.remove(position);
                                projects.remove(position);
                                firstImages.remove(position);
                                rv.removeViewAt(position);
                                listAllProjectAdapter.notifyItemRemoved(position);
                                listAllProjectAdapter.notifyItemRangeChanged(position, firstImages.size());
                                listAllProjectAdapter.notifyDataSetChanged();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });



            }
        });
    }

    @Override
    public int getItemCount() {
        return this.projects.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder{

        protected SwipeRevealLayout currentProjectNameCardView;
        protected TextView currentProjectName;
        protected TextView playPrototype;
        protected TextView editPrototype;
        private String TAG = "ProjectViewHolder";


        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            currentProjectName = itemView.findViewById(R.id.single_project_name);
            currentProjectNameCardView = itemView.findViewById(R.id.single_project);
            playPrototype = itemView.findViewById(R.id.play_prototype);
            editPrototype = itemView.findViewById(R.id.delete_prototype);


        }
    }

    private void removeImage(String path){
        StorageReference storageRef = storage.getReference();

// Create a reference to the file to delete
        StorageReference desertRef = storageRef.child(path);


// Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                Log.d(TAG, path + " DELETED");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.e(TAG, path + " failed");
            }
        });
    }

}
