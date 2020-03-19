package com.example.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app.db.dbClasses.Characters;

import java.util.List;

@Dao
public interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Characters characters);

    @Delete()
    void deleteCharacter(Characters characters);

    @Query("SELECT * FROM Characters WHERE id=:id")
    Characters getCharacterById(int id);

    @Query("SELECT * FROM Characters")
    LiveData<List<Characters>> getCharacters();

    @Query("SELECT COUNT(*) FROM Characters")
    int getCount();
}
