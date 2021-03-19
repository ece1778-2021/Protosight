package com.example.protosight.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TaskResult implements Parcelable {

    private String projectCode, testCode, taskID, videoPath, tester;

    private String firstQuestion, secondQuestion, thirdQuestion, fourthQuestion, fifthQuestion;

    private String firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, fifthAnswer;


    public TaskResult(String tester, String projectCode, String testCode, String taskID, String videoPath) {
        this.projectCode = projectCode;
        this.testCode = testCode;
        this.taskID = taskID;
        this.videoPath = videoPath;
        this.tester = tester;

        this.firstQuestion = "";
        this.secondQuestion = "";
        this.thirdQuestion = "";
        this.fourthQuestion = "";
        this.fifthQuestion = "";

        this.firstAnswer = "";
        this.secondAnswer = "";
        this.thirdAnswer = "";
        this.fourthAnswer = "";
        this.fifthAnswer = "";


    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public String getFifthAnswer() {
        return fifthAnswer;
    }

    public void setFifthAnswer(String fifthAnswer) {
        this.fifthAnswer = fifthAnswer;
    }

    protected TaskResult(Parcel in) {
        projectCode = in.readString();
        testCode = in.readString();
        taskID = in.readString();
        videoPath = in.readString();
        tester = in.readString();
        firstQuestion = in.readString();
        secondQuestion = in.readString();
        thirdQuestion = in.readString();
        fourthQuestion = in.readString();
        fifthQuestion = in.readString();
    }

    public static final Creator<TaskResult> CREATOR = new Creator<TaskResult>() {
        @Override
        public TaskResult createFromParcel(Parcel in) {
            return new TaskResult(in);
        }

        @Override
        public TaskResult[] newArray(int size) {
            return new TaskResult[size];
        }
    };

    public String getTester() {
        return tester;
    }






    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getTestCode() {
        return testCode;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getFirstQuestion() {
        return firstQuestion;
    }

    public void setFirstQuestion(String firstQuestion) {
        this.firstQuestion = firstQuestion;
    }

    public String getSecondQuestion() {
        return secondQuestion;
    }

    public void setSecondQuestion(String secondQuestion) {
        this.secondQuestion = secondQuestion;
    }

    public String getThirdQuestion() {
        return thirdQuestion;
    }

    public void setThirdQuestion(String thirdQuestion) {
        this.thirdQuestion = thirdQuestion;
    }

    public String getFourthQuestion() {
        return fourthQuestion;
    }

    public void setFourthQuestion(String fourthQuestion) {
        this.fourthQuestion = fourthQuestion;
    }

    public String getFifthQuestion() {
        return fifthQuestion;
    }

    public void setFifthQuestion(String fifthQuestion) {
        this.fifthQuestion = fifthQuestion;
    }

    public Map<String, String> toMap(){
        Map<String, String> res = new HashMap<>();
        res.put("projectCode", getProjectCode());
        res.put("testCode", getTestCode());
        res.put("tester", getTester());
        res.put("taskID", getTaskID());
        res.put("videoPath", getVideoPath());
        res.put("firstAnswer", getFirstAnswer());
        res.put("secondAnswer", getSecondAnswer());
        res.put("thirdAnswer", getThirdAnswer());
        res.put("fourthAnswer", getFourthAnswer());
        res.put("fifthAnswer", getFifthAnswer());

        return res;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(projectCode);
        dest.writeString(testCode);
        dest.writeString(taskID);
        dest.writeString(videoPath);
        dest.writeString(tester);
        dest.writeString(firstQuestion);
        dest.writeString(secondQuestion);
        dest.writeString(thirdQuestion);

        dest.writeString(fourthQuestion);
        dest.writeString(fifthQuestion);

    }


}
