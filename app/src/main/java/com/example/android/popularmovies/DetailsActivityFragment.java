package com.example.android.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aiman Nabeel on 05/04/18
 */

/**********************************************************************************************
        *    This code has been adapted from the following source:
        *    Title: PopularMovies
        *    Author: Ravi Sravan Kumar
        *    Date: 2018
        *    Code version: N/A
        *    Availability: https://github.com/ravisravan89/PopularMovies
        ***************************************************************************************/


public class DetailsActivityFragment extends Fragment /*implements LoaderManager.LoaderCallbacks<Cursor>*/ {

    LinearLayoutManager LayoutManager;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView header;
    Movie movieObject;
    long movieid;
    ContentResolver contentResolver;
    private static final int FAVMOVIE_LOADER_ID = 0;

    final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
    //TODO Remove this key before uploading the project
    String api_key_value = "faaa06f746cc46c17d321731163eaae2";
    final int MOVIE_PURPOSE_TRAILER = 1;
    final int MOVIE_PURPOSE_REVIEWS = 2;
    final String MOVIE_TRAILER_QUERY = "/videos?";
    final String MOVIE_REVIEW_QUERY = "/reviews?";


    //Inflating layout for fragment_details, Favorite checkbox and calling bindDataToView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        if (getArguments() != null) {
            movieObject = (Movie) getArguments().getSerializable("movieObject");
            if (NetworkUtils.isNetworkAvailable(getActivity())) {
                //Using Handler class to delay loading this view, so it's ready for all the responses and shows them every time a movie is clicked
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestServer(MOVIE_TRAILER_QUERY, MOVIE_PURPOSE_TRAILER, movieObject.getId());
                    }
                }, 200);
                //Instead of just this line of code
                //requestServer(MOVIE_TRAILER_QUERY, MOVIE_PURPOSE_TRAILER, movieObject.getId());
            } else {
                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        }

        //Setting up Favorite Checkbox
        final CheckBox FavoriteCheckBox = (CheckBox) view.findViewById(R.id.isFavoriteCheckBox);
        FavoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                preferences.edit().putBoolean(String.valueOf(movieObject.getId()), isChecked).apply();

            }
        });
        final boolean isFavourite = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean(String.valueOf(movieObject.getId()), false);
        FavoriteCheckBox.setChecked(isFavourite);


        //Implementing onClickListener on the "Mark as Favorite" button and linking it with the Content Resolver
        FavoriteCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                List<ContentValues> list = new ArrayList<ContentValues>();
                ContentValues cv = new ContentValues();

                if (isFavourite == true) {


                    cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE, movieObject.getTitle());
                    cv.put(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_ID, movieObject.getId());
                    list.add(cv);

                    //Inserting new FavMovie data via ContentResolver
                    Uri uri = getActivity().getContentResolver().insert(FavoriteMovieListContract.ListEntry.CONTENT_URI, cv);
                    //if (uri == null) Toast.makeText(baseContext.getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();


                } else {

                    //Deleting
                    int id = (int) movieObject.getId();
                    String stringId = Integer.toString(id);
                    Uri uri = FavoriteMovieListContract.ListEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(stringId).build();

                    context.getContentResolver().delete(uri, null, null);

                    //Updating
                    //context.getContentResolver().update(uri, cv, null,null);
                }



            }

        });


        bindDataToView(view);
        return view;
    }


    //Setting up DetailActivity layout, ActionBar, CollapsingToolbar, Backdrop image, etc.
    //References from fragment_detail.xml
    //Ref: http://saulmm.github.io/mastering-coordinator
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (getArguments() != null) {
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.anim_toolbar);
            if (toolbar != null) {
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
                toolbar.hideOverflowMenu();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.toolbar_color));
        collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.toolbar_color_dark));
        int titleColor = getResources().getColor(R.color.white);
        collapsingToolbar.setCollapsedTitleTextColor(titleColor);
        header = (ImageView) view.findViewById(R.id.header);
        if (getArguments() != null) {
            Picasso.with(getActivity()).load(movieObject.getBackdrop()).placeholder(R.drawable.no_poster).into(header);
            Picasso.with(getActivity())
                    .load(movieObject.getBackdrop())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    if (isAdded() && isVisible()) {
                                        int color = palette.getMutedColor(getResources().getColor(R.color.toolbar_color));
                                        collapsingToolbar.setContentScrimColor(color);
                                        collapsingToolbar.setStatusBarScrimColor(color);
                                        int titleColor = palette.getVibrantColor(getResources().getColor(R.color.white));
                                        collapsingToolbar.setCollapsedTitleTextColor(titleColor);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
            collapsingToolbar.setTitle(movieObject.getTitle());
            TextView title = (TextView) getView().findViewById(R.id.title);
            if (title != null) {
                title.setText(movieObject.getTitle());
            }
        }

    }


    //Extracting movie data individually and binding it to their respective Views. And then calling their respective methods from Movie.class
    private void bindDataToView(View view) {
        //setting release date
        setValuesToView(view.findViewById(R.id.release_date), getString(R.string.release_date), movieObject.getReleaseDate());
        //setting original language
        setValuesToView(view.findViewById(R.id.original_language), getString(R.string.original_language), movieObject.getOriginalLanguage());
        //setting  movie overview
        setValuesToView(view.findViewById(R.id.overview), getString(R.string.overview), movieObject.getDescription());
        //setting average ratigns
        setValuesToView(view.findViewById(R.id.average_rating), getString(R.string.average_rating), "Approx: " + movieObject.getVoteAverage());
        //setting popularity
        setValuesToView(view.findViewById(R.id.popularity), getString(R.string.popularity), "Approx: " + movieObject.getPopularity());
        //setting total rating
        setValuesToView(view.findViewById(R.id.total_rating), getString(R.string.total_rating), "Approx: " + movieObject.getVoteCount());
        //setting show review
        setValuesToView(view.findViewById(R.id.reviews), getString(R.string.reviews), getString(R.string.read_reviews));
        view.findViewById(R.id.reviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(getActivity())) {
                    //Passing empty string will get movies list sorted by original title in ascending order.
                    requestServer(MOVIE_REVIEW_QUERY, MOVIE_PURPOSE_REVIEWS, movieObject.getId());
                } else {
                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Setting up Title and Data TextViews via detail_recycler_item.xml
    private void setValuesToView(View view, String heading, String value) {
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView data = (TextView) view.findViewById(R.id.data);
        title.setText(heading);
        data.setText(value);
    }

    //Building Uri with API key, parameters and values
    private void requestServer(String queryKey, final int purpose, long movieId) {
        final String GET_TRAILER_URL = MOVIE_BASE_URL + movieId + queryKey;
        String api_key = "api_key";
        Uri builtUri = Uri.parse(GET_TRAILER_URL).buildUpon()
                .appendQueryParameter(api_key, api_key_value).build();
        String URL = builtUri.toString();

        //Passing second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponseBasedOnMoviePurpose(purpose, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
        //Adding the request object for execution to the queue
        RequestManager.getInstance(getActivity()).addToRequestQueue(req);
    }


    //Populating trailers and reviews for a specific movie, setting onClick method to start intent via watchYoutubeTrailer method
    private void parseResponseBasedOnMoviePurpose(int purpose, JSONObject response) {
        if (isAdded() && isVisible())
            switch (purpose) {
                case MOVIE_PURPOSE_TRAILER:
                    final VideoTrailersResponse trailerResponse = new Gson().fromJson(response.toString(), VideoTrailersResponse.class);
                    int trailers = trailerResponse.getTrailerObjectAL().size();
                    if (trailers == 0) {
                        ((TextView) getView().findViewById(R.id.trailers).findViewById(R.id.trailerdata)).setText(getString(R.string.no_trailers));
                        Log.w("myApp", "no trailers/network");
                    } else {
                        String trailersFound = getResources().getQuantityString(R.plurals.trailers_qty, trailers, trailers);
                        ((TextView) getView().findViewById(R.id.trailerdata)).setText(trailersFound);
                        LinearLayout trailers_view = (LinearLayout) getView().findViewById(R.id.trailers);
                        for (final VideoTrailersResponse.TrailerObject trailerObject : trailerResponse.getTrailerObjectAL()) {
                            View view = View.inflate(getActivity(), R.layout.trailer_item_view, null);
                            TextView textView = (TextView) view.findViewById(R.id.trailername);
                            textView.setText(trailerObject.getName());
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    watchYoutubeTrailer(trailerObject.getKey());
                                }
                            });
                            trailers_view.addView(view);
                        }
                    }
                    break;
                case MOVIE_PURPOSE_REVIEWS:
                    final ReviewsResponse reviewsResponse = new Gson().fromJson(response.toString(), ReviewsResponse.class);
                    int reviews = reviewsResponse.getTotalresults();
                    if (reviews > 0) {
                        Intent intent = new Intent(getActivity(), ReviewsActivity.class);
                        intent.putExtra("title", movieObject.getTitle());
                        intent.putExtra("reviewsObject", reviewsResponse);
                        startActivity(intent);
                    } else {
                        ((TextView) getView().findViewById(R.id.reviews).findViewById(R.id.data)).setText(getString(R.string.no_reviews));
                    }
                    break;
            }

    }

    //Setting up intent to watch movie trailers on Youtube
    public void watchYoutubeTrailer(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

}


