package com.example.onlinestationeryshop;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GoodDao {
    @Insert
    void insert(Good good);

    @Query("SELECT * FROM good WHERE name LIKE '%' || :name || '%'")
    List<Good> getByName(String name);

    @Query("SELECT * FROM good")
    List<Good> getAll();

    @Query("DELETE FROM good")
    void deleteAll();

    @Query("SELECT * FROM good WHERE idg =:id")
    Good getByID(int id);


    @Query("SELECT count(id) FROM good")
    int getCount();

    @Update
    void update(Good good);
}
