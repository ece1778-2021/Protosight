package com.example.protosight.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class TheTask implements Parcelable {

    private String tester;
    private String videoPath;
    private ArrayList<String> questions;
    private ArrayList<String> answers;



    public TheTask(String tester, String videoPath,ArrayList<String> questions, ArrayList<String> answers){
        this.tester = tester;
        this.videoPath = videoPath;
        this.questions = questions;
        this.answers = answers;

    }


    public String getTester(){return this.tester;}
    public String getVideoPath(){return this.videoPath;}

    public ArrayList<String[]> toArray(){
        ArrayList<String[]> output= new ArrayList<String[]>();
        for (int i=0; i<questions.size(); i++){
            output.add(new String[]{questions.get(i), answers.get(i)});
        }
        return output;
    }



    protected TheTask(Parcel in) {
        this.tester = in.readString();
        this.videoPath = in.readString();
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        in.readList(questions, String.class.getClassLoader());
        in.readList(answers, String.class.getClassLoader());
    }

    public static final Creator<TheTask> CREATOR = new Creator<TheTask>() {
        @Override
        public TheTask createFromParcel(Parcel in) {
            return new TheTask(in);
        }

        @Override
        public TheTask[] newArray(int size) {
            return new TheTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tester);
        dest.writeString(videoPath);
        dest.writeList(questions);
        dest.writeList(answers);
    }
}
