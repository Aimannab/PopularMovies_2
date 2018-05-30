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
        //public static final String COLUMN_DATE = "date";

        /**
         * Returns just the selection part of the favorites query from a normalized today value.
         * This is used to get a favorites from today's date. To make this easy to use
         * in compound selection, we embed today's date as an argument in the query.
         *
         * @return The selection part of the favorites query for today onwards
         */
        /*public static String getSqlSelectForTodayOnwards() {
            long normalizedUtcNow = normalizeDate(System.currentTimeMillis());
            return FavoriteMovieListContract.ListEntry.COLUMN_DATE + " >= " + normalizedUtcNow;
        }

        public static final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);

        public static long normalizeDate(long date) {
            long daysSinceEpoch = elapsedDaysSinceEpoch(date);
            long millisFromEpochToTodayAtMidnightUtc = daysSinceEpoch * DAY_IN_MILLIS;
            return millisFromEpochToTodayAtMidnightUtc;
        }

        private static long elapsedDaysSinceEpoch(long utcDate) {
            return TimeUnit.MILLISECONDS.toDays(utcDate);
        }*/
    }
}

