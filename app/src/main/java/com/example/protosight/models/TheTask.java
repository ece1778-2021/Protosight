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

//    public void setFirstAnswer(String answer){ this.firstAnswer = answer; }
//    public void setSecondAnswer(String answer){this.secondAnswer = answer; }
//    public void setThirdAnswer(String answer){this.thirdAnswer = answer; }
//    public void setFourthAnswer(String answer){this.fourthAnswer = answer; }
//    public void setFifthAnswer(String answer){this.fifthAnswer = answer; }
//
//    public void setFirstQuestion(String answer){ this.firstQuestion = answer; }
//    public void setSecondQuestion(String answer){this.secondQuestion = answer; }
//    public void setThirdQuestion(String answer){this.thirdQuestion = answer; }
//    public void setFourthQuestion(String answer){this.fourthQuestion = answer; }
//    public void setFifthQuestion(String answer){this.fifthQuestion = answer; }

//    public void setQuestionByArray(ArrayList<String> questions, ArrayList<String> answers){
//        this.questions = questions;
//        this.answers = answers;
//    }

    public String getTester(){return this.tester;}
    public String getVideoPath(){return this.videoPath;}

    public ArrayList<String[]> toArray(){
//        String[] iterable = new String[]{firstQuestion, secondQuestion, thirdQuestion, fourthQuestion, fifthQuestion};
//        String[] answers = new String[]{firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, fifthAnswer};
        ArrayList<String[]> output= new ArrayList<String[]>();
        for (int i=0; i<questions.size(); i++){
            output.add(new String[]{questions.get(i), answers.get(i)});
        }
        return output;
    }



    protected TheTask(Parcel in) {
        this.tester = in.readString();
        this.videoPath = in.readString();
//        this.firstAnswer = in.readString();
//        this.secondAnswer = in.readString();
//        this.thirdAnswer = in.readString();
//        this.fourthAnswer = in.readString();
//        this.fifthAnswer = in.readString();
//
//        this.firstQuestion = in.readString();
//        this.secondQuestion = in.readString();
//        this.thirdQuestion = in.readString();
//        this.fourthQuestion = in.readString();
//        this.fifthQuestion = in.readString();
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
//        dest.writeString(firstAnswer);
//        dest.writeString(secondAnswer);
//        dest.writeString(thirdAnswer);
//        dest.writeString(fourthAnswer);
//        dest.writeString(fifthAnswer);
//
//        dest.writeString(firstQuestion);
//        dest.writeString(secondQuestion);
//        dest.writeString(thirdQuestion);
//        dest.writeString(fourthQuestion);
//        dest.writeString(fifthQuestion);
        dest.writeList(questions);
        dest.writeList(answers);
    }
}
