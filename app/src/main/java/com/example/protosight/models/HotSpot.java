package com.example.protosight.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class HotSpot implements Parcelable {

    private int x;
    private int y;
    private int w;
    private int h;
    private String relatedImage;
    private String linkImage;
    private boolean isFirst;
    private String projectID;
    private String creator;



    public HotSpot(int x, int y, int w, int h, String relatedImage) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.relatedImage = relatedImage;
        this.linkImage = "";
        this.isFirst = false;
        this.projectID = "";
        this.creator = "";
    }

    protected HotSpot(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        w = in.readInt();
        h = in.readInt();
        relatedImage = in.readString();
    }

    public static final Creator<HotSpot> CREATOR = new Creator<HotSpot>() {
        @Override
        public HotSpot createFromParcel(Parcel in) {
            return new HotSpot(in);
        }

        @Override
        public HotSpot[] newArray(int size) {
            return new HotSpot[size];
        }
    };

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getRelatedImage() {
        return relatedImage;
    }

    public void setRelatedImage(String relatedImage) {
        this.relatedImage = relatedImage;
    }

    @Override
    public String toString() {
        return "HotSpot{" +
                "(" + x +
                "," + y +
                "," + w +
                "," + h + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(w);
        dest.writeInt(h);
        dest.writeString(relatedImage);
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        this.isFirst = first;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> res = new HashMap<>();
        res.put("screenshotFrom", getRelatedImage());
        res.put("screenshotTo", getLinkImage());
        res.put("isFirst", isFirst());
        res.put("creator", getCreator());
        res.put("x", getX());
        res.put("y", getY());
        res.put("w", getW());
        res.put("h", getH());
        res.put("projectID", getProjectID());

        return res;


    }

}
