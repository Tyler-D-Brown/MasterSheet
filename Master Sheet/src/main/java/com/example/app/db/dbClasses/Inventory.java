package com.example.app.db.dbClasses;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    public Inventory(int id, String name, String description, double qty, String type, int rating, String skillName, int character, boolean starred){
        this.id=id;
        this.name=name;
        this.description=description;
        this.qty=qty;
        this.type=type;
        this.rating=rating;
        this.skillName=skillName;
        this.character=character;
        this.starred=starred;
    }

    @Ignore
    public Inventory(){}

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
}
