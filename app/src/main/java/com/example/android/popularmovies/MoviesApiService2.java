package com.example.android.popularmovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Aiman Nabeel on 16/04/2018.
 */

//Executing "Top-Rated Movies" search through this interface, called in MainActivity.onOptionsItemSelected method
public interface MoviesApiService2 {
    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);
}
