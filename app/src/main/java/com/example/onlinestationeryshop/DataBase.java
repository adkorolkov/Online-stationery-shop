package com.example.onlinestationeryshop;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Config.class, History.class, Order.class, OrderContent.class, Good.class, Cart.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract ConfigDao configDao();
    public abstract HistoryDao historyDao();
    public abstract GoodDao goodDao();
    public abstract CartDao cartDao();
    public abstract OrderDao orderDao();
    public abstract OrderContentDao orderContentDao();
}
