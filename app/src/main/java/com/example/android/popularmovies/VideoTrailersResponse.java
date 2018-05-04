package com.example.android.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aiman Nabeel on 11/04/2018.
 */

@SuppressWarnings("ALL")
public class VideoTrailersResponse implements Serializable {

    @SerializedName("results")
    private ArrayList<TrailerObject> trailerObjectArrayList;

    @SerializedName("id")
    private int id;

    public ArrayList<TrailerObject> getTrailerObjectAL() {
        return trailerObjectArrayList;
    }

    //Methods to set up Movie Trailers option, called in DetailActivityFragment.parseResponseBasedOnPurpose method
    public class TrailerObject implements Serializable {
        @SerializedName("id")
        private String trailerId;

        @SerializedName("iso_639_1")
        private boolean isoString;

        @SerializedName("key")
        private String key;

        @SerializedName("name")
        private String name;

        @SerializedName("site")
        private String site;

        @SerializedName("size")
        private int size;

        @SerializedName("type")
        private String type;

        public String getTrailerId() {
            return trailerId;
        }

        public boolean isoString() {
            return isoString;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getSite() {
            return site;
        }

        public int getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }


}
