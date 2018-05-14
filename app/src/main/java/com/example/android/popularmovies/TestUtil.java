package com.example.android.popularmovies;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aiman Nabeel on 08/05/2018.
 */

//Adding fake data to try out the Menu Button
public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db) {




        if (db == null) {
            return;
        }
        //Creating a list of Movie Favorites
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();

        //Testing with static data
        /*cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE, "Avengers: Infinity War");
        cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID, 299536);
        list.add(cv);

        //cv = new ContentValues();
        cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE, input);
        cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID, movieid);
        list.add(cv);

        Uri uri = getContentResolver().insert(FavoriteMovieListContract.ListEntry.CONTENT_URI, cv);

        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }*/

        //Insert all favorites in one transaction
        try {
            db.beginTransaction();
            //clear the table first
            db.delete(FavoriteMovieListContract.ListEntry.TABLE_NAME, null, null);
            //go through the list and add one by one
            for (ContentValues c : list) {
                db.insert(FavoriteMovieListContract.ListEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //too bad :(
        } finally {
            db.endTransaction();
        }
    }

}


