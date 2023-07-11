package com.cookmaster.classes;

public class Conversation {

    private String toName;
    private int toId;

    private String urlImage;

    public Conversation(String toName, int toId, String urlImage) {
        this.toName = toName;
        this.toId = toId;
        this.urlImage = urlImage;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

}
