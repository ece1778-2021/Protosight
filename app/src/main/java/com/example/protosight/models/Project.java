package com.example.protosight.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;
import java.util.List;

public class Project{

    private String projectName;
    private ArrayList<String > images;

    private String  currentImage;


    public Project(String projectName, ArrayList<String > images) {
        this.projectName = projectName;
        this.images = images;
        this.currentImage = "";
    }


    public void setCurrentImage(String currentImage){
        this.currentImage = currentImage;
    }

    public String getCurrentImage(){
        return this.currentImage;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getProjectName() {
        return projectName;
    }

}
