package com.example.android.popularmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.popularmovies.FavoriteMovieListContract.ListEntry.TABLE_NAME;


/**
 * Created by Aiman Nabeel on 11/05/2018.
 */

public class FavoritesContentProvider extends ContentProvider {

    private FavoriteMoviesDbHelper moviesDbHelper;
    public static final int FAVORITES = 100;
    public static final int FAVORITES_WITH_ID = 101;

    //Creating static global variable, to access it throughout your Provider code
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //Adding matches as follows: addURI(String authority, String path, int code)
        //For directory
        uriMatcher.addURI(FavoriteMovieListContract.AUTHORITY, FavoriteMovieListContract.PATH_FAVORTIES, FAVORITES);
        //For a single item
        uriMatcher.addURI(FavoriteMovieListContract.AUTHORITY, FavoriteMovieListContract.PATH_FAVORTIES + "/#", FAVORITES_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {

        Context context = getContext();
        moviesDbHelper = new FavoriteMoviesDbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //Geting access to underlying database, for query(Read-only)
        final SQLiteDatabase db = moviesDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        //Query for all FAVORITES
        switch (match) {
            case FAVORITES:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        //throw  new UnsupportedOperationException("Not yet implemented");

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case FAVORITES:
            //Inserting values in FAVORITES table
                long id = db.insert(TABLE_NAME, null, values);
                if(id > 0) {
                    //success
                    returnUri = ContentUris.withAppendedId(FavoriteMovieListContract.ListEntry.CONTENT_URI, id);
                }
                else {
                    throw new SQLException("Failed to insert new row into " + uri);
                }

            break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri );
        }

        //Notify the reslover if the uri has been changed
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
