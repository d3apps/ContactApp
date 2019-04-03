package com.example.denni.contactapp;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {

    private String id;
    private String msg;
    private String key;

    public Message() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Message(String id, String msg, String key) {
        this.id = id;
        this.msg = msg;
        this.msg = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
