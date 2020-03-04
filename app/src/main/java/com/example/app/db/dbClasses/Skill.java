package com.example.app.db.dbClasses;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "skills", primaryKeys = {"character", "name"})
public class Skill {
    private int character;
    private String name;
    private double rank;
    private String description;
    private String attribute;

    public Skill(int character, String name, double rank, String description, String attribute){
        this.character=character;
        this.name=name;
        this.rank=rank;
        this.description=description;
        this.attribute=attribute;
    }

    @Ignore
    public Skill(){}

    public int getCharacter(){return character;}

    public void setCharacter(int character){this.character=character;}

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public double getRank(){return rank;}

    public void setRank(double rank){this.rank=rank;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public String getAttribute(){return attribute;}

    public void setAttribute(String attribute){this.attribute=attribute;}
}
