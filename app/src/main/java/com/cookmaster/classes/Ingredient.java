package com.cookmaster.classes;

public class Ingredient {

    private String name;
    private Float quantity;
    private String unit = "";

    public Ingredient(String name, Float quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = (float) quantity;
        this.unit = unit;
    }

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = (float) quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return (quantity + ' ' + (unit.isEmpty() ? name : unit + ' ' + name));
    }
}
