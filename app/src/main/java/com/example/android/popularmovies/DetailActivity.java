package com.example.android.popularmovies;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Aiman Nabeel on 13/03/18
 */

public class DetailActivity extends AppCompatActivity {

    private String mMovieDetailString;
    private TextView mMovieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Creating a Detail Activity Fragment and add it to the activity
        if (savedInstanceState == null) {
            Movie movieObject = (Movie) getIntent().getSerializableExtra("movieObject");

            Bundle arguments = new Bundle();

            arguments.putSerializable("movieObject", movieObject);

            DetailsActivityFragment fragment = new DetailsActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mMovieDetailTextView, fragment)
                    .commit();
        }
    }

        // When the home button is pressed, take the user back to the MainActivity
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
    }



