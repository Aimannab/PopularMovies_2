package com.example.android.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aiman Nabeel on 30/04/2018.
 */

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FavoriteMovies.db";
    private static final int DATABASE_VERSION = 1;


    //Calling constructor
    public FavoriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORTIE_MOVIESLIST_TABLE = "CREATE TABLE " +
                FavoriteMovieListContract.ListEntry.TABLE_NAME + " (" +
                FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                //FavoriteMovieListContract.ListEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE + " TEXT NOT NULL, " +
                "UNIQUE " + "(" + FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID + ")" + " ON CONFLICT REPLACE" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORTIE_MOVIESLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + FavoriteMovieListContract.ListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}

