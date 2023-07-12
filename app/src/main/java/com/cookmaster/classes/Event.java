package com.cookmaster.classes;

public class Event {
    private String name;
    private String description;
    private String date;

    private String chefUsername;

    private String location;

    private boolean inscrit;
    private String recipeName;



    public Event(String name, String description, String date, String chefUsername, String location, boolean inscrit, String recipeName) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.chefUsername = chefUsername;
        this.location = location;
        this.inscrit = inscrit;
        this.recipeName = recipeName;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public String getChefUsername() {
        return this.chefUsername;
    }

    public String getLocation() {
        return this.location;
    }

    public boolean getInscrit() {
        return this.inscrit;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setChefUsername(String chefUsername) {
        this.chefUsername = chefUsername;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInscrit(boolean inscrit) {
        this.inscrit = inscrit;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String eventToJson(){
        String json = "{";
        json += "\"name\":\"" + name + "\",";
        json += "\"description\":\"" + description + "\",";
        json += "\"date\":\"" + date + "\",";
        json += "\"chef_username\":\"" + chefUsername + "\",";
        json += "\"location\":\"" + location + "\",";
        json += "\"inscrit\":\"" + inscrit + "\",";
        json += "\"recipe_name\":\"" + recipeName + "\"";
        json += "}";
        return json;
    }
}
