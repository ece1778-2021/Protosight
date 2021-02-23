package com.example.protosight.models;

public class HotSpot {

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
}
