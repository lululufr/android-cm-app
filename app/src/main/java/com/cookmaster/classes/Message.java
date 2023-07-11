package com.cookmaster.classes;

public class Message {

    private String fromName;

    private int fromId;

    private String toName;

    private int toId;

    private String content;

    private String date;

    public Message(String fromName, int fromId, String toName, int toId, String content, String date) {
        this.fromName = fromName;
        this.fromId = fromId;
        this.toName = toName;
        this.toId = toId;
        this.content = content;
        this.date = date;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
