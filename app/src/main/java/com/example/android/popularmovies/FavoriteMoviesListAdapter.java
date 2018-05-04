package com.example.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Aiman Nabeel on 30/04/2018.
 */

public class FavoriteMoviesListAdapter extends RecyclerView.Adapter<FavoriteMoviesListAdapter.FavoritesViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    // COMPLETED (8) Add a new local variable mCount to store the count of items to be displayed in the recycler view
    private int mCount;

    // COMPLETED (9) Update the Adapter constructor to accept an integer for the count along with the context
    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    public FavoriteMoviesListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        // COMPLETED (10) Set the local mCount to be equal to count
        this.mCursor = cursor;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.favorite_list_item, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;
        //Calling Favorite Movie names here
        String name = mCursor.getString(mCursor.getColumnIndex(FavoriteMovieListContract.ListEntry.COLUMN_NAME_MOVIE_TITLE));
        holder.nameTextView.setText(name);

    }


    // COMPLETED (11) Modify the getItemCount to return the mCount value rather than 0
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class FavoritesViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView partySizeTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link FavoritesListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public FavoritesViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }
}
