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

    private AppRepository(Context context){
        db = AppDatabase.getInstance(context);
        characters = getAllCharacters();
    }

    private LiveData<List<Characters>> getAllCharacters(){ return db.character().getCharacters(); }

    private LiveData<List<Skill>> getCharacterSkills(int character){
        return db.skills().getCharacterSkills(character);
    }

    private LiveData<List<Inventory>> getCharacterInventory(int character){
        return db.inventory().getCharacterInventory(character);
    }

    public Characters getCharacterById(int id){
        inventory = getCharacterInventory(id);
        skills = getCharacterSkills(id);
        return db.character().getCharacterById(id);
    }

    public void insertCharacter(final Characters characters){
        executor.execute(new Runnable(){
            @Override
            public void run(){
                db.character().insertCharacter(characters);
            }
        });
    }

    public void deleteCharacter(final Characters character){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.character().deleteCharacter(character);
            }
        });
    }
}
