package com.example.onlinestationeryshop.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long insert(Order order);


    @Query("DELETE FROM ordertable")
    void deleteAll();

    @Query("SELECT * FROM ordertable ORDER BY time DESC")
    List<Order> getAll();

    @Query("SELECT * FROM ordertable WHERE id =:id")
    Order getByID(int id);


    @Update
    void update(Order order);
}
