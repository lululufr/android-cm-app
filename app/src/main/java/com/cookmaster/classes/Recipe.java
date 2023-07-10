package com.cookmaster.classes;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;

    private ArrayList<Ingredient> ingredientList;

    private ArrayList<String> tagList;

    private String description;

    private String urlImage;

    public Recipe(String name, ArrayList<Ingredient> ingredientList, ArrayList<String> tagList, String description, String urlImage) {
        this.name = name;
        this.ingredientList = ingredientList;
        this.tagList = tagList;
        this.description = description;
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return (name + " " + ingredientList + " " + tagList + " " + description + " " + urlImage);
    }
}
