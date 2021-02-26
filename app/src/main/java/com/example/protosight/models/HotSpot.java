package com.example.protosight.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HotSpot implements Parcelable {

    private int x;
    private int y;
    private int w;
    private int h;
    private String relatedImage;

    public HotSpot(int x, int y, int w, int h, String relatedImage) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.relatedImage = relatedImage;
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
                ") w=" + w +
                ", h=" + h;
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
}
