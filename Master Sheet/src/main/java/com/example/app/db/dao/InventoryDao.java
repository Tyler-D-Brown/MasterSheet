package com.example.app.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app.db.dbClasses.Inventory;

import java.util.List;

@Dao
public interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInventory(Inventory item);

    @Delete
    void deleteItem(Inventory item);

    @Query("SELECT * FROM Inventory WHERE id = :id")
    Inventory getInventoryById(int id);

    @Query("SELECT * FROM Inventory WHERE name MATCH :selection OR description MATCH :selection OR type MATCH :selection")
    LiveData<List<Inventory>> searchInventory(String selection);

    @Query("SELECT * FROM Inventory WHERE character = :character")
    LiveData<List<Inventory>> getCharacterInventory(int character);

    @Query("SELECT * FROM Inventory WHERE character = :character AND starred = 1")
    LiveData<List<Inventory>> getStarredCharacterInventory(int character);

    @Query("DELETE FROM Inventory WHERE character = :character")
    void deleteCharacterInventory(int character);
}
