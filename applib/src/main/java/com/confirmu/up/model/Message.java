package com.confirmu.up.model;

import org.json.JSONArray;

import java.util.List;

public class Message {
    private String message;
    private int userId;
    private String type = "";
    private List<String> list;

    private JSONArray mcqArray;

    public Message(String message, int userId) {
        this.message = message;
        this.userId = userId;
        this.type = "";
    }

    public Message(String message, int userId, String type) {
        this.message = message;
        this.userId = userId;
        this.type = type;
    }

    public Message(JSONArray mcqArray, int userId) {
        this.mcqArray = mcqArray;
        this.userId = userId;
        this.type = type;
    }

    public Message(List<String> list, int userId) {
        this.list = list;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public JSONArray getMcqArray() {
        return mcqArray;
    }

    public void setMcqArray(JSONArray mcqArray) {
        this.mcqArray = mcqArray;
    }
}
