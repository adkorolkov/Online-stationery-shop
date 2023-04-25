package com.example.onlinestationeryshop;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(History history);

    @Query("SELECT * FROM searchhistory ORDER BY time DESC LIMIT :limit")
    List<History> getLast(int limit);

    @Query("SELECT * FROM searchhistory WHERE quer = :quer")
    History getBySeach(String quer);

    @Query("DELETE FROM searchhistory")
    void deleteAll();

    @Query("DELETE FROM searchhistory WHERE quer = :quer")
    void delete(String quer);
    @Update
    void update(History history);

}
