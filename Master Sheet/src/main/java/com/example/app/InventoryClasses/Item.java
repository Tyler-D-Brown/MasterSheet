package com.example.app.InventoryClasses;

public class Item {
    private int id;
    private String name;
    private String description;
    private double qty;
    private int character;

    public Item(int id, String name, String description, double qty, int character) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.character = character;
    }

    public Item(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }
}


