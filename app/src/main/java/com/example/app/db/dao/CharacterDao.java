package com.example.app.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app.db.dbClasses.Character;

import java.util.List;

@Dao
public interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Character character);

    @Delete
    void deleteCharacter(Character character);

    @Query("SELECT * FROM Character WHERE id=:id")
    Character getCharacterById(int id);

    @Query("SELECT * FROM Character")
    List<Character> getCharacters();

    @Query("SELECT COUNT(*) FROM Character")
    int getCount();
}
