package com.example.hiddentreasure.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.hiddentreasure.models.TreasureItem;

@Database(entities = {TreasureItem.class}, version = 1)
public abstract class TreasureDatabase extends RoomDatabase {
    private static TreasureDatabase instance;

    public abstract TreasureDAO mTreasureDAO();

    public static synchronized TreasureDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TreasureDatabase.class,
                    "treasure_db"
            )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            final TreasureDAO treasureDAO = instance.mTreasureDAO();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    treasureDAO.insertTreasure(new TreasureItem("Pull-up bar", "Good for pulling"));
                    treasureDAO.insertTreasure(new TreasureItem("Nintendo Switch", "Play BotW"));
                }
            }).start();
        }
    };
}
