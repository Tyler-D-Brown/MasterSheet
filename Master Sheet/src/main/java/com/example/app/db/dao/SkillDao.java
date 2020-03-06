package com.example.app.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app.db.dbClasses.Skill;

import java.util.List;

@Dao
public interface SkillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSkill(Skill skill);

    @Delete
    void deleteSkill(Skill skill);

    @Query("SELECT * FROM skills WHERE character = :character")
    LiveData<List<Skill>> getCharacterSkills(int character);

    @Query("SELECT * FROM skills WHERE character = :character AND name = :name")
    Skill getSkill(int character, String name);

    @Query("SELECT * FROM skills WHERE name MATCH :selection OR description MATCH :selection OR attribute MATCH :selection")
    LiveData<List<Skill>> searchSkills(String selection);

    @Query("SELECT * FROM skills WHERE character = :character AND starred = 1")
    LiveData<List<Skill>> getStarredCharacterSkills(int character);
}
