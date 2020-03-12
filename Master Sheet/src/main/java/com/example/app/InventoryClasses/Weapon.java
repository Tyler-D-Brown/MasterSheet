package com.example.app.InventoryClasses;

public class Weapon extends Item {
    private String skillName;
    private boolean starred;

    public Weapon(int id, String name, String description, double qty, int character, String skillName, boolean starred) {
        super(id, name, description, qty, character);
        this.skillName=skillName;
        this.starred=starred;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }
}
