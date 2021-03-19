package com.example.protosight.models;

public class InstructionItem {
    String Description;
    int screenImg;

    public InstructionItem(String description, int screenImg) {
        Description = description;
        this.screenImg = screenImg;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getScreenImg() {
        return screenImg;
    }

    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }
}
