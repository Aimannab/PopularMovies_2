package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private FavoriteMoviesListAdapter mAdapter;
    private SQLiteDatabase mDb;
    Movie movieObject;
    long movieid;

    private static final String TAG = FavoriteListActivity.class.getSimpleName();
    private static final int FAVMOVIE_LOADER_ID = 0;

    public static final String[] MAIN_FAVORITES_PROJECTION = {
            FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_favorite_movies_view);

        RecyclerView favlistRecyclerView;
        favlistRecyclerView = new RecyclerView(this);

        favlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_favorite_movies_view);

        favlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FavoriteMoviesDbHelper dbHelper = new FavoriteMoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllFavoriteMovies();
        mAdapter = new FavoriteMoviesListAdapter(this, cursor);

        //Linking the FavoritesMovies adapter to RecyclerView
        favlistRecyclerView.setAdapter(mAdapter);

        //Restarting loader - Favorites Checkbox
        getSupportLoaderManager().restartLoader(FAVMOVIE_LOADER_ID, null, this);

    }


    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(FAVMOVIE_LOADER_ID, null, this);
    }


    /**
     * Instantiates and returns a new AsyncTaskLoader with the given ID.
     * This loader will return task data as a Cursor or null if an error occurs.
     *
     * Implements the required callbacks to take care of loading data at all stages of loading.
     */


    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        /*switch (id) {
            case FAVMOVIE_LOADER_ID:
                Uri favoriteQueryUri = FavoriteMovieListContract.ListEntry.CONTENT_URI;
                String sortOrder = FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE + " ASC";
                String selection = FavoriteMovieListContract.ListEntry.getSqlSelectForTodayOnwards();
                //String selection = null;

                return new CursorLoader(this,
                        favoriteQueryUri,
                        MAIN_FAVORITES_PROJECTION,
                        selection,
                        null,
                        sortOrder
                        );
                default:
                    throw new RuntimeException("Loader not implemented: " + id);
        }*/

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mFavMovieData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mFavMovieData != null) {
                    // Delivers any previously loaded data immediately
                    
                    deliverResult(mFavMovieData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(FavoriteMovieListContract.ListEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mFavMovieData = data;
                super.deliverResult(data);
            }
        };

    }


    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        // Updating the data that the adapter uses to create ViewHolders
        mAdapter.swapCursor(data);
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }



    private Cursor getAllFavoriteMovies() {
        return mDb.query(FavoriteMovieListContract.ListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
