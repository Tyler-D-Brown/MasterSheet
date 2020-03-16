package com.example.app.InventoryClasses;

import com.example.app.db.dbClasses.Inventory;

public class Weapon extends Item{
    private String skillName;
    private boolean starred;

    public Weapon(int id, String name, String description, double qty, int character, String skillName, boolean starred) {
        super(id, name, description, qty, character);
        this.skillName=skillName;
        this.starred=starred;
    }

    public Weapon(Inventory value) {
        super(value.getId(), value.getName(), value.getDescription(), value.getQty(), value.getCharacter());
        this.skillName=value.getSkillName();
        this.starred= value.isStarred();
    }

    public Weapon(){
        super(-1, "", "", -1, -1);
        this.skillName = "";
        this.starred=false;
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
