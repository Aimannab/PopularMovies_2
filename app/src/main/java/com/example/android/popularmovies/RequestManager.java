package com.example.android.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by Aiman Nabeel on 11/04/2018.
 */

/**********************************************************************************************
 *    This code has been adapted from the following source:
 *    Title: PopularMovies
 *    Author: Ravi Sravan Kumar
 *    Date: 2018
 *    Code version: N/A
 *    Availability: https://github.com/ravisravan89/PopularMovies
 ***************************************************************************************/

public class RequestManager {

    /**
     * tag for using in logs.
     */
    public static final String TAG = RequestManager.class.getSimpleName();

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    @SuppressLint("StaticFieldLeak")
    private static RequestManager sInstance;

    private static final Object slock = new Object();

    private Context context;

    private RequestManager(Context context){
        this.context = context.getApplicationContext();
    }

    /**
     * returns an instance of request manager. if not available creates one and returns it.
     * @param context
     * @return
     */
    public static RequestManager getInstance(Context context){
        if(sInstance==null){
            synchronized (slock){
                if(sInstance==null){
                    sInstance = new RequestManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
