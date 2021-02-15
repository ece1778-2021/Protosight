package com.example.protosight.models;

import java.util.HashMap;
import java.util.Map;

public class Creator {

    private String username;
    private String email;
    private String uid;

    public Creator(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<String, String> toMap(){
        Map<String, String> res = new HashMap<>();
        res.put("email", getEmail());
        res.put("uid", getUid());
        res.put("username", getUsername());
        return res;
    }


}
