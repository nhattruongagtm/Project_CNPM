package com.example.project_cnpm.Database;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Database {

    private static Database volleyPool;
    private final RequestQueue requestQueue;

    private Database(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static Database getInstance(Context context) {
        if (volleyPool == null) {
            volleyPool = new Database(context);
        }
        return volleyPool;
    }

    public <T> void excuteQuery(Request<T> request) {
        requestQueue.add(request);
    }

}
