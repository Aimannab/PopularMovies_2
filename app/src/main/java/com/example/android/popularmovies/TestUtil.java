package com.example.android.popularmovies;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aiman Nabeel on 08/05/2018.
 */

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //Create a list of fake Movie Favorites
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE, "Avengers: Infinity War");
        cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID, 299536);
        list.add(cv);

        /*cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Tim");
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Jessica");
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Larry");
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, "Kim");
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, 45);
        list.add(cv);*/

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (FavoriteMovieListContract.ListEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(FavoriteMovieListContract.ListEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }


}
