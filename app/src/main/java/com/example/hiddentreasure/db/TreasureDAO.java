package com.example.hiddentreasure.db;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.hiddentreasure.models.TreasureItem;

import java.util.List;

@Dao
interface TreasureDAO {
    @Query("SELECT * FROM treasure")
    List<TreasureItem> getAllTreasures();
}
