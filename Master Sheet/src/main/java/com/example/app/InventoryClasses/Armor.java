package com.example.app.InventoryClasses;

import com.example.app.db.dbClasses.Inventory;

public class Armor extends Item{
    private int rating;
    private String armorLocation;

    public Armor(int id, String name, String description, double qty, int character, int rating, String armorLocation) {
        super(id, name, description, qty, character);
        this.rating=rating;
        this.armorLocation=armorLocation;
    }

    public Armor(){
        super(-1, "", "", -1, -1);
        this.rating = -1;
        this.armorLocation = "";
    }

    public Armor(Inventory value) {
        super(value.getId(), value.getName(), value.getDescription(), value.getQty(), value.getCharacter());
        this.rating = value.getRating();
        this.armorLocation = value.getArmorLocation();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getArmorLocation() {
        return armorLocation;
    }

    public void setArmorLocation(String armorLocation) {
        this.armorLocation = armorLocation;
    }
}
