package com.example.app.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.app.db.dao.CharacterDao;
import com.example.app.db.dao.InventoryDao;
import com.example.app.db.dao.SkillDao;
import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

@Database(entities={Characters.class, Inventory.class, Skill.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "MasterSheet.db";
    private static volatile AppDatabase instance;
    private static final Object Lock = new Object();

    public abstract SkillDao skills();
    public abstract InventoryDao inventory();
    public abstract CharacterDao character();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized(Lock){
                if(instance==null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
