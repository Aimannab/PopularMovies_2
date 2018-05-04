package com.example.android.popularmovies;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aiman Nabeel on 07/03/18
 */

public class Movie implements Serializable {

    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("title")
    private String title;

    @SerializedName("page")
    private int page;

    @SerializedName("adult")
    private boolean isAdult;


    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("genre_ids")
    private int [] genre_ids;

    @SerializedName("id")
    private long id;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("vote_count")
    private long voteCount;

    @SerializedName("video")
    private boolean hasVideo;

    @SerializedName("vote_average")
    private float voteAverage;

    public Movie() {}


    //Methods to set up MainActivity and DetailActivity attributes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() { return TMDB_IMAGE_PATH + poster;}

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return TMDB_IMAGE_PATH + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public int getPage() {
        return page;
    }

    public boolean isAdult() {
            return isAdult;
        }

    public String getReleaseDate() { return releaseDate; }

    public int[] getGenre_ids() {
            return genre_ids;
        }

    public long getId() {
            return id;
        }

    public String getOriginalLanguage() {
            return originalLanguage;
        }

    public float getPopularity() {
            return popularity;
        }

    public long getVoteCount() {
            return voteCount;
        }

    public boolean isHasVideo() {
            return hasVideo;
        }

    public float getVoteAverage() {
            return voteAverage;
        }


    //Inner class for Movie List
    public static class MovieResult implements Serializable {

        @SerializedName("results")
        private ArrayList<Movie> results;

        public ArrayList<Movie> getResults() {
            return results;
        }
    }



}
