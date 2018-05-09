package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.FavoriteListActivity;
import com.example.android.popularmovies.Movie;
import com.example.android.popularmovies.Movie.MovieResult;
import com.example.android.popularmovies.MoviesAdapter;
import com.example.android.popularmovies.MoviesApiService;
import com.example.android.popularmovies.MoviesApiService2;
import com.example.android.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Aiman Nabeel on 28/02/18
 */

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler {

    Toolbar toolbar;
    String mSortingOrder = "";
    boolean movieTwoPane;
    Movie movieObject;


    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;

    //Creating Main Toolbar and Navigation Drawer via activity_base.xml
    //Ref: http://mateoj.com/2015/06/21/adding-toolbar-and-navigation-drawer-all-activities-android/
    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullViewFeature = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        //activity_base.xml has been id-ied as activity_content
        FrameLayout activityContainer = (FrameLayout) fullViewFeature.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullViewFeature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Popular Movies");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    //OnCreate Method and Implementations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //Creating Grid Layout
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //spanCount refers to number of columns in a grid
        mAdapter = new MoviesAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i<25; i++) {
            movies.add(new Movie());
        }

        mAdapter.setMovieList(movies);


        //Creating RestAdapter
        //Ref: http://mateoj.com/2015/10/07/creating-movies-app-retrofit-picass-android-part2/
        //TODO Remove this key before uploading the project
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("api_key", "faaa06f746cc46c17d321731163eaae2");
            }
        })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Initializing MoviesApiService interface
        MoviesApiService service = restAdapter.create(MoviesApiService.class);

        service.getPopularMovies(new Callback<MovieResult>() {
            @Override
            public void success(MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();

            }
        });

    }

    //Creating a Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sort, menu);
        return true;
    }

    //Making Menu sort movies according to "Most Popular" or "Top-Rated" categories
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_most_popular) {
            //Load Most Popular Movies data method

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.themoviedb.org/3")
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addEncodedQueryParam("api_key", "faaa06f746cc46c17d321731163eaae2");
                        }
                    })
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            MoviesApiService service = restAdapter.create(MoviesApiService.class);

            service.getPopularMovies(new Callback<MovieResult>() {
                @Override
                public void success(MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());

                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();

                }
            });

            //Testing Toast message
            /*Context context = MainActivity.this;
            String message = "Most Popular Movies Clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();*/

            return true;
        }

        if (id == R.id.action_top_rated) {
            //Load Top-Rated Movies data method

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.themoviedb.org/3")
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addEncodedQueryParam("api_key", "faaa06f746cc46c17d321731163eaae2");
                        }
                    })
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            MoviesApiService2 service = restAdapter.create(MoviesApiService2.class);

            service.getTopRatedMovies(new Callback<MovieResult>() {
                @Override
                public void success(MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());

                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();

                }
            });


            //Testing Toast message
            /*Context context = MainActivity.this;
            String message = "Top-Rated Movies Clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();*/

            return true;
        }

        if (id == R.id.action_favorite) {

            //Add FavoriteListActivity here
            Intent favoritesListIntent = new Intent(this, FavoriteListActivity.class);
            //favoritesListIntent.putExtra("title", movieObject.getTitle());                          //check this!! try replacing it with movieobject OR geAllFavMovies() query
            startActivity(favoritesListIntent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMovieItemClicked(Movie movieObject) {

        //if false
        if (movieTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            View view = findViewById(R.id.mMovieDetailTextView);
            if(view.getVisibility()==View.INVISIBLE) {
                findViewById(R.id.mMovieDetailTextView).setVisibility(View.VISIBLE);
            }
        }
        else {
            Intent detailsIntent = new Intent(this, DetailActivity.class);
            detailsIntent.putExtra("movieObject", movieObject);
            startActivity(detailsIntent);
        }
    }

}
