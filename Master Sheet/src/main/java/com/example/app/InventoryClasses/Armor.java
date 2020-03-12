package com.example.app.InventoryClasses;

public class Armor extends Item{
    private int rating;
    private String armorLocation;

    public Armor(int id, String name, String description, double qty, int character, int rating, String armorLocation) {
        super(id, name, description, qty, character);
        this.rating=rating;
        this.armorLocation=armorLocation;
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
