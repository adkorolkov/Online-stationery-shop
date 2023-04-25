package com.example.onlinestationeryshop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface ConfigDao {
    @Insert
    void insert(Config config);

    @Query("SELECT * FROM config WHERE name = :name")
    Config getByName(String name);


    @Query("DELETE FROM config")
    void deleteAll();

    @Query("DELETE FROM config WHERE name = :name")
    void delete(String name);
    @Update
    void update(Config config);

}
