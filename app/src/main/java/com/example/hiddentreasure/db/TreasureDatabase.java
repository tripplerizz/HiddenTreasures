package com.example.hiddentreasure.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.hiddentreasure.models.TreasureItem;

@Database(entities = {TreasureItem.class}, version = 1)
abstract class TreasureDatabase extends RoomDatabase {

}
