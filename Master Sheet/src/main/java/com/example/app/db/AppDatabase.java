package com.example.app.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.app.db.dao.CharacterDao;
import com.example.app.db.dao.InventoryDao;
import com.example.app.db.dao.SkillDao;
import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

@Database(entities={Characters.class, Inventory.class, Skill.class}, version=4)
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
                        AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build(); //.addMigrations(MIGRATION_4_5)
                }
            }
        }
        return instance;
    }

    //TODO Fix this to remove bloat on the Characters table
    /*static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE Characters_new (id TEXT, name TEXT, race TEXT, " +
                    "build TEXT, strength REAL, dexterity REAL, agility REAL, intelligence REAL, " +
                    "will REAL, constitution REAL, charisma REAL, headDamage INTEGER, " +
                    "torsoDamage INTEGER, rightArmDamage INTEGER, leftArmDamage INTEGER, " +
                    "rightLegDamage INTEGER, leftLegDamage INTEGER, gold REAL, PRIMARY KEY(id))");
            // Copy the data
            database.execSQL("INSERT INTO Characters_new (id, name, race, build, strength, " +
                    "dexterity, agility, intelligence, will, constitution, charisma, headDamage, " +
                    "torsoDamage, rightArmDamage, leftArmDamage, rightLegDamage, leftLegDamage, " +
                    "gold) SELECT id, name, race, build, strength, dexterity, agility, " +
                    "intelligence, will, constitution, charisma, headDamage, torsoDamage, " +
                    "rightArmDamage, leftArmDamage, rightLegDamage, leftLegDamage, gold " +
                    "FROM Characters");
                    // Remove the old table
                    database.execSQL("DROP TABLE Characters");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE Characters_new RENAME TO Characters");
        }
    };*/
}
