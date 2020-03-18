package com.example.app.db.dbClasses;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.app.InventoryClasses.Armor;
import com.example.app.InventoryClasses.Item;
import com.example.app.InventoryClasses.Weapon;

@Entity(tableName = "Inventory", foreignKeys =
        {
            @ForeignKey(entity = Skill.class, parentColumns = {"name", "character"}, childColumns = {"skillName", "character"}),
            @ForeignKey(entity = Characters.class, parentColumns = "id", childColumns = "character")
        })
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private double qty;
    private String type;
    private int rating;
    private String skillName;
    private int character;
    private boolean starred;
    private String armorLocation;

    public Inventory(int id, String name, String description, double qty, String type, int rating, String skillName, int character, boolean starred, String armorLocation){
        if(id != -1) {
            this.id = id;
        }
        this.name=name;
        this.description=description;
        this.qty=qty;
        this.type=type;
        this.rating=rating;
        this.skillName=skillName;
        this.character=character;
        this.starred=starred;
        this.armorLocation = armorLocation;
    }

    @Ignore
    public Inventory(){}

    @Ignore
    public Inventory(Armor item){
        if(id != -1) {
            this.id = item.getId();
        }
        this.name=item.getName();
        this.description=item.getDescription();
        this.qty=item.getQty();
        this.type="Armor";
        this.rating=item.getRating();
        this.character=item.getCharacter();
        this.armorLocation=item.getArmorLocation();
    }

    @Ignore
    public Inventory(Item item){
        if(id != -1) {
            this.id = item.getId();
        }
        this.name=item.getName();
        this.description=item.getDescription();
        this.qty=item.getQty();
        this.type="Item";
        this.character=item.getCharacter();
    }

    @Ignore
    public Inventory(Weapon weapon){
        if(id != -1) {
            this.id = weapon.getId();
        }
        this.name=weapon.getName();
        this.description=weapon.getDescription();
        this.qty=weapon.getQty();
        this.type="Weapon";
        this.skillName=weapon.getSkillName();
        this.character=weapon.getCharacter();
        this.starred=weapon.isStarred();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skill) {
        this.skillName = skill;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getArmorLocation() {
        return armorLocation;
    }

    public void setArmorLocation(String armorLocation) {
        this.armorLocation = armorLocation;
    }
}
