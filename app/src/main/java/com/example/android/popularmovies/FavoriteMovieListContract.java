package com.example.android.popularmovies;

import android.provider.BaseColumns;

/**
 * Created by sumairzamir on 30/04/2018.
 */

public class FavoriteMovieListContract {
    private FavoriteMovieListContract () {}

    public static class ListEntry implements BaseColumns {
        public static final String TABLE_NAME = "Favorite Movies List";
        public static final String COLUMN_NAME_MOVIE_TITLE = "title";
        public static final String COLUMN_NAME_MOVIE_ID = "id";

    }
}

