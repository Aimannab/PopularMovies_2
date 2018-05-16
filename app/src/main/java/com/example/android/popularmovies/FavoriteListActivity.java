package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {

    private FavoriteMoviesListAdapter mAdapter;
    private SQLiteDatabase mDb;
    Movie movieObject;
    long movieid;
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

        FavoriteMoviesDbHelper dbHelper = new FavoriteMoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        //Inserting fake data
        //TestUtil.insertFakeData(mDb);

        //if (mDb == null) {
        //    return;
        //}

        Cursor cursor = getAllFavoriteMovies();
        mAdapter = new FavoriteMoviesListAdapter(this, cursor);

        //Linking the FavoritesMovies adapter to RecyclerView
        favlistRecyclerView.setAdapter(mAdapter);

    }




    //Not required. Check DetailActivityFragment code
    /*public void onMovieMarkedAsFavorite(View view) {

        CheckBox favCheckbox = findViewById(R.id.isFavoriteCheckBox);

        /*if (((CheckBox) findViewById(R.id.isFavoriteCheckBox)).isChecked()) {
            movieid = movieObject.getId();

        }*/

        /*favCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieid = movieObject.getId();
            }
        });

        String input = ((TextView) findViewById(R.id.title)).getText().toString();
        //else {
            //What to do it Favorite Checkbox unchecked?
        //}

        //Creating a list of Movie Favorites
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE, input);
        cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID, movieid);
        list.add(cv);

        //Inserting new FavMovie data via ContentResolver
        Uri uri = getContentResolver().insert(FavoriteMovieListContract.ListEntry.CONTENT_URI, cv);

        //Checking if data is being successfullyinserted by displaying URI
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        //finish();

    }*/

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
