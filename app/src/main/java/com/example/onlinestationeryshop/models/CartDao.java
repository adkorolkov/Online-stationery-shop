package com.example.onlinestationeryshop.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);

    @Query("SELECT * FROM cart WHERE goodid = :id")
    Cart getByGoodId(int id);

    @Query("SELECT * FROM cart")
    List<Cart> getAll();


    @Query("SELECT count FROM cart WHERE goodid = :id")
    int getItemCount(int id);


    @Update
    void update(Cart cart);


    @Query("SELECT sum(c.count) FROM cart AS c")
    int getCartCount();

    @Query("SELECT sum(price*count) FROM cart")
    int getCartPrice();

    @Query("DELETE FROM cart")
    void deleteCartAll();

    @Query("SELECT count(id) FROM cart")
    int getCount();

    @Query("DELETE FROM cart WHERE goodid =:id")
    void deleteById(int id);


}
