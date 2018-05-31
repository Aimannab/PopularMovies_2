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
    private int mCount;

    public FavoriteMoviesListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Getting the RecyclerView item layout
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

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class FavoritesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that is inflated in
         *                 {@link FavoriteMoviesListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public FavoritesViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
        }

    }

    //Method swapCursor for implemeting in FavoriteListActivity class
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
}
