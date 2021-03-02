package com.example.protosight.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Project{

    private String projectName;
    private ArrayList<String > images;
    private String creator, creatorEmail, projectCode,firstImageRef;
    private long timestamp;

    private String  currentImage;

    public Project(){
        this.projectName = "";
        this.images = new ArrayList<>();
        this.currentImage = "";
        this.creator = "";
        this.creatorEmail = "";
        this.projectCode = "";
        this.firstImageRef = "";
        this.timestamp = 1234567890;
    }


    public Project(String projectName){
        this.projectName = projectName;
        this.images = new ArrayList<>();
        this.currentImage = "";
        this.creator = "";
        this.creatorEmail = "";
        this.projectCode = "";
        this.firstImageRef = "";
        this.timestamp = 1234567890;
    }


    public Project(String projectName, ArrayList<String > images) {
        this.projectName = projectName;
        this.images = images;
        this.currentImage = "";
        this.creator = "";
        this.creatorEmail = "";
        this.projectCode = "";
        this.firstImageRef = "";
        this.timestamp = 1234567890;
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

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getFirstImageRef() {
        return firstImageRef;
    }

    public void setFirstImageRef(String firstImageRef) {
        this.firstImageRef = firstImageRef;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> res = new HashMap<>();
        res.put("name", getProjectName());
        res.put("creator", getCreator());
        res.put("creatorEmail", getCreatorEmail());
        res.put("projectCode", getProjectCode());
        res.put("firstImageRef", getFirstImageRef());
        res.put("timestamp", getTimestamp());

        return  res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return timestamp == project.timestamp &&
                projectName.equals(project.projectName) &&
                images.equals(project.images) &&
                creator.equals(project.creator) &&
                creatorEmail.equals(project.creatorEmail) &&
                projectCode.equals(project.projectCode) &&
                firstImageRef.equals(project.firstImageRef) &&
                currentImage.equals(project.currentImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, images, creator, creatorEmail, projectCode, firstImageRef, timestamp, currentImage);
    }

    public void clear(){
        this.projectName = "";
        this.images = new ArrayList<>();
        this.currentImage = "";
        this.creator = "";
        this.creatorEmail = "";
        this.projectCode = "";
        this.firstImageRef = "";
        this.timestamp = 1234567890;
    }
}
