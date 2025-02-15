package com.example.app.db.dbClasses;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Characters")
public class Characters {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String race;
    private String build;
    private double strength;
    private double dexterity;
    private double agility;
    private double intelligence;
    private double will;
    private double constitution;
    private double charisma;
    private int headDamage;
    private int torsoDamage;
    private int rightArmDamage;
    private int leftArmDamage;
    private int rightLegDamage;
    private int leftLegDamage;
    private int headArmor;
    private int torsoArmor;
    private int rightArmArmor;
    private int leftArmArmor;
    private int rightLegArmor;
    private int leftLegArmor;
    private double gold;

    public Characters(int id, String name, String race, String build, double strength,
                      double dexterity, double agility, double intelligence, double will,
                      double constitution, double charisma, int headDamage, int torsoDamage,
                      int rightArmDamage, int leftArmDamage, int rightLegDamage, int leftLegDamage,
                      int headArmor, int torsoArmor, int rightArmArmor, int leftArmArmor,
                      int rightLegArmor, int leftLegArmor, double gold) {
        if(id != -1) {
            this.id = id;
        }
        this.name = name;
        this.race = race;
        this.build = build;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.intelligence = intelligence;
        this.will = will;
        this.constitution = constitution;
        this.charisma = charisma;
        this.headDamage = headDamage;
        this.torsoDamage = torsoDamage;
        this.rightArmDamage = rightArmDamage;
        this.leftArmDamage = leftArmDamage;
        this.rightLegDamage = rightLegDamage;
        this.leftLegDamage = leftLegDamage;
        this.headArmor = headArmor;
        this.torsoArmor = torsoArmor;
        this.rightArmArmor = rightArmArmor;
        this.leftArmArmor = leftArmArmor;
        this.rightLegArmor = rightLegArmor;
        this.leftLegArmor = leftLegArmor;
        this.gold = gold;
    }

    @Ignore
    public Characters(){}

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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getDexterity() {
        return dexterity;
    }

    public void setDexterity(double dexterity) {
        this.dexterity = dexterity;
    }

    public double getAgility() {
        return agility;
    }

    public void setAgility(double agility) {
        this.agility = agility;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = intelligence;
    }

    public double getWill() {
        return will;
    }

    public void setWill(double will) {
        this.will = will;
    }

    public double getConstitution() {
        return constitution;
    }

    public void setConstitution(double constitution) {
        this.constitution = constitution;
    }

    public double getCharisma() {
        return charisma;
    }

    public void setCharisma(double charisma) {
        this.charisma = charisma;
    }

    public int getHeadDamage() {
        return headDamage;
    }

    public void setHeadDamage(int headDamage) {
        this.headDamage = headDamage;
    }

    public int getTorsoDamage() {
        return torsoDamage;
    }

    public void setTorsoDamage(int torsoDamage) {
        this.torsoDamage = torsoDamage;
    }

    public int getRightArmDamage() {
        return rightArmDamage;
    }

    public void setRightArmDamage(int rightArmDamage) {
        this.rightArmDamage = rightArmDamage;
    }

    public int getLeftArmDamage() {
        return leftArmDamage;
    }

    public void setLeftArmDamage(int leftArmDamage) {
        this.leftArmDamage = leftArmDamage;
    }

    public int getRightLegDamage() {
        return rightLegDamage;
    }

    public void setRightLegDamage(int rightLegDamage) {
        this.rightLegDamage = rightLegDamage;
    }

    public int getLeftLegDamage() {
        return leftLegDamage;
    }

    public void setLeftLegDamage(int leftLegDamage) {
        this.leftLegDamage = leftLegDamage;
    }

    public int getHeadArmor() {
        return headArmor;
    }

    public void setHeadArmor(int headArmor) {
        this.headArmor = headArmor;
    }

    public int getTorsoArmor() {
        return torsoArmor;
    }

    public void setTorsoArmor(int torsoArmor) {
        this.torsoArmor = torsoArmor;
    }

    public int getRightArmArmor() {
        return rightArmArmor;
    }

    public void setRightArmArmor(int rightArmArmor) {
        this.rightArmArmor = rightArmArmor;
    }

    public int getLeftArmArmor() {
        return leftArmArmor;
    }

    public void setLeftArmArmor(int leftArmArmor) {
        this.leftArmArmor = leftArmArmor;
    }

    public int getRightLegArmor() {
        return rightLegArmor;
    }

    public void setRightLegArmor(int rightLegArmor) {
        this.rightLegArmor = rightLegArmor;
    }

    public int getLeftLegArmor() {
        return leftLegArmor;
    }

    public void setLeftLegArmor(int leftLegArmor) {
        this.leftLegArmor = leftLegArmor;
    }

    public double getGold() {return gold;}

    public void setGold(double gold) {this.gold = gold;}
}
