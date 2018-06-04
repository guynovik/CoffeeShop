/*
package com.example.guy.projectbagrut;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

*/
/**
 * Created by Guy on 29.1.2018.
 *//*


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "place.db";
    public static final String TABLE_NAME = "place_table";
    public static final String COL_1 = "Restaurants";
    public static final String COL_2 = "Coffee Shops";
    public static final String COL_3 = "Movie Theatres";
    public static final String COL_4 = "Pizza Places";


    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(RESTAURANTS STRING PRIMARY KEY AUTOINCREMENT, COFFEE SHOPS TEXT, MOVIE THEATRES TEXT, PIZZA PLACES TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
*/
