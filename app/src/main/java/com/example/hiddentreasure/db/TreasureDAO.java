package com.example.hiddentreasure.db;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hiddentreasure.models.TreasureItem;

import java.util.List;

@Dao
public interface TreasureDAO {
    @Query("SELECT * FROM treasure")
    LiveData<List<TreasureItem>> getAllTreasures();

    @Insert
    void insertTreasure(TreasureItem item);

    @Update
    void updateTreasure(TreasureItem item);

    @Delete
    void deleteTreasure(TreasureItem item);
}
