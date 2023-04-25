package com.example.onlinestationeryshop;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderContentDao {
    @Insert
    void insert(OrderContent orderContent);


    @Query("DELETE FROM ordercontent")
    void deleteAll();

    @Query("SELECT * FROM ordercontent WHERE orderid =:orderid")
    List<OrderContent> getByOrderID(int orderid);



    @Update
    void update(OrderContent orderContent);
}
