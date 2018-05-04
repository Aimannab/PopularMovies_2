package com.example.android.popularmovies;


import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Aiman Nabeel on 12/03/18
 */

//Executing "Popular Movies" search through this interface, also called in MainActivity.onOptionsItemSelected method
public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);
}


