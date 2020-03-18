package com.example.app.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<Characters>> characters;
    public LiveData<List<Inventory>> inventory;
    public LiveData<List<Skill>> skills;
    private AppDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context){
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    public AppRepository(Context context){
        db = AppDatabase.getInstance(context);
        characters = getAllCharacters();
    }

    public LiveData<List<Characters>> getAllCharacters(){ return db.character().getCharacters(); }

    public LiveData<List<Skill>> getCharacterSkills(int character){
        return db.skills().getCharacterSkills(character);
    }

    public LiveData<List<Inventory>> getCharacterInventory(int character){
        return db.inventory().getCharacterInventory(character);
    }

    public LiveData<List<Skill>>getStarredCharacterSkills(int characterId){
        return db.skills().getStarredCharacterSkills(characterId);
    }

    public LiveData<List<Inventory>>getStarredCharacterInventory(int characterId){
        return db.inventory().getStarredCharacterInventory(characterId);
    }

    public Characters getCharacterById(int id){
        inventory = getCharacterInventory(id);
        skills = getCharacterSkills(id);
        return db.character().getCharacterById(id);
    }

    public Skill getSkill(String name, int characterId){
        return db.skills().getSkill(characterId, name);
    }

    public void saveSkill(final Skill save){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.skills().insertSkill(save);
            }
        });
    }

    public void trainSkill(String name, int character){
        Skill skill = db.skills().getSkill(character, name);
        skill.setRank(skill.getRank()+.1);
        db.skills().insertSkill(skill);
    }

    public void insertCharacter(final Characters characters){
        executor.execute(new Runnable(){
            @Override
            public void run(){
                db.character().insertCharacter(characters);
            }
        });
    }

    public void deleteCharacter(final int character){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.character().deleteCharacter(db.character().getCharacterById(character));
            }
        });
    }

    public void deleteSkill(final Skill skill) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.skills().deleteSkill(skill);
            }
        });
    }

    public Inventory getItemById(int itemId) {
        return db.inventory().getInventoryById(itemId);
    }

    public void saveInventory(Inventory newItem) {
        db.inventory().insertInventory(newItem);
    }

    public void deleteInventory(final Inventory value) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.inventory().deleteItem(value);
            }
        });
    }

    public LiveData<List<String>> getSkillNames(int character){
        return db.skills().getCharacterSkillNames(character);
    }
}
