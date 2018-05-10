package com.example.android.popularmovies;

import android.provider.BaseColumns;

/**
 * Created by Aiman Nabeel on 30/04/2018.
 */

public class FavoriteMovieListContract {
    private FavoriteMovieListContract () {}

    public static class ListEntry implements BaseColumns {
        //Always use table_name with an underscore otherwise gives error e.g. Favorite_Movies_List
        public static final String TABLE_NAME = "Favorite_Movies_List";
        public static final String COLUMN_NAME_MOVIE_TITLE = "title";
        public static final String COLUMN_NAME_MOVIE_ID = "id";

    }
}

