package com.example.android.popularmovies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class FavoriteListActivity extends AppCompatActivity {

    private FavoriteMoviesListAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        RecyclerView favlistRecyclerView;

        favlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_favorite_movies_view);

        favlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavoriteMoviesDbHelper dbHelper = new FavoriteMoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllFavoriteMovies();
        mAdapter = new FavoriteMoviesListAdapter(this, cursor);

        //Linking the FavoritesMovies adapter to RecyclerView
        favlistRecyclerView.setAdapter(mAdapter);

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
