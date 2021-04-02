package com.example.hiddentreasure.db;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.hiddentreasure.models.TreasureItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Database(entities = {TreasureItem.class}, version = 1, exportSchema = false)
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
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    byte[] pullUpbarImg;
                    byte[] nintendoSwitchImg;
                    byte[] incenseImg;
                    byte[] cat;

                    try {
                        pullUpbarImg = getByteArrayImage("https://cdn.shopify.com/s/files/1/0997/2134/products/Main1024x1024_1200x.jpg?v=1578687773");
                        nintendoSwitchImg = getByteArrayImage("https://images-na.ssl-images-amazon.com/images/I/61-PblYntsL._AC_SL1500_.jpg");
                        incenseImg = getByteArrayImage("https://images-na.ssl-images-amazon.com/images/I/51l0T9gY3NL._AC_SX522_.jpg");
                        cat = getByteArrayImage("https://c.files.bbci.co.uk/12A9B/production/_111434467_gettyimages-1143489763.jpg");

                        // sample data to test database
                        treasureDAO.insertTreasure(new TreasureItem("Pull-up bar", "Good for pulling", pullUpbarImg));
                        treasureDAO.insertTreasure(new TreasureItem("Nintendo Switch", "Play BotW", nintendoSwitchImg));
                        treasureDAO.insertTreasure(new TreasureItem("Incense", "For the praying", incenseImg));
                        treasureDAO.insertTreasure(new TreasureItem("Cat", "Pls take care of it", cat));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static byte[] getByteArrayImage(String url) throws IOException {
        URL imageUrl = new URL(url);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream inputStream = imageUrl.openStream()) {
            int n;
            byte[] buffer = new byte[1024];
            while ((n = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
        }
        return output.toByteArray();
    }
}
