package com.example.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

public class FavoriteListActivity extends AppCompatActivity {

    private FavoriteMoviesListAdapter mAdapter;
    private SQLiteDatabase mDb;
    //Context context = FavoriteListActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite_list); --wrong id
        setContentView(R.layout.all_favorite_movies_view);

        RecyclerView favlistRecyclerView;
        favlistRecyclerView = new RecyclerView(this);

        favlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_favorite_movies_view);

        favlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //favlistRecyclerView.setLayoutManager(layoutManager);
        //RecyclerView waitlistRecyclerView;
        // Set local attributes to corresponding views
        //waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        //waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        FavoriteMoviesDbHelper dbHelper = new FavoriteMoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        //Inserting fake data
        TestUtil.insertFakeData(mDb);

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
