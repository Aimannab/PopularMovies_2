package com.example.android.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.concurrent.TimeUnit;

/**
 * Created by Aiman Nabeel on 30/04/2018.
 */

public class FavoriteMovieListContract {
    //Defining contract for all of our constants

    //The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.popularmovies";

    //The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //This is the path for the Favorites_Movies_List directory
    public static final String PATH_FAVORTIES = "Favorite_Movies_List";

    private FavoriteMovieListContract () {}

    //Inner class that defines the contents of the Favorites_Movies_List table
    public static class ListEntry implements BaseColumns {

        //ListEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORTIES).build();

        //Always use table_name with an underscore otherwise gives error e.g. Favorite_Movies_List
        public static final String TABLE_NAME = "Favorite_Movies_List";
        public static final String COLUMN_NAME_MOVIE_TITLE = "title";
        public static final String COLUMN_NAME_MOVIE_ID = "id";
    }
}

