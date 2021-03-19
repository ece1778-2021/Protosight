package com.example.protosight.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TaskResult implements Parcelable {

    private String projectCode, testCode, taskID, videoPath, tester;

    private String firstQuestion, secondQuestion, thirdQuestion, fourthQuestion, fifthQuestion;


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
        res.put("firstQuestion", getFirstQuestion());
        res.put("secondQuestion", getSecondQuestion());
        res.put("thirdQuestion", getThirdQuestion());
        res.put("fourthQuestion", getFourthQuestion());
        res.put("fifthQuestion", getFifthQuestion());

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskResult that = (TaskResult) o;
        return Objects.equals(projectCode, that.projectCode) &&
                Objects.equals(testCode, that.testCode) &&
                Objects.equals(taskID, that.taskID) &&
                Objects.equals(videoPath, that.videoPath) &&
                Objects.equals(tester, that.tester) &&
                Objects.equals(firstQuestion, that.firstQuestion) &&
                Objects.equals(secondQuestion, that.secondQuestion) &&
                Objects.equals(thirdQuestion, that.thirdQuestion) &&
                Objects.equals(fourthQuestion, that.fourthQuestion) &&
                Objects.equals(fifthQuestion, that.fifthQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, testCode, taskID, videoPath, tester, firstQuestion, secondQuestion, thirdQuestion, fourthQuestion, fifthQuestion);
    }
}
