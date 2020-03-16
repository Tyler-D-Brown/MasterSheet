package com.example.app.InventoryClasses;

import com.example.app.db.dbClasses.Inventory;

public class Item {
    private int id;
    private String name;
    private String description;
    private double qty;
    private int character;

    public Item(int id, String name, String description, double qty, int character) {
        if(id !=-1) {
            this.id = id;
        }
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.character = character;
    }

    public Item(Inventory item){
        if(id !=-1) {
            this.id = item.getId();
        }
        this.name=item.getName();
        this.description=item.getDescription();
        this.qty=item.getQty();
        this.character=item.getCharacter();
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


