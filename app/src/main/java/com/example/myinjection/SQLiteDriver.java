package com.example.myinjection;

import android.util.Log;

public class SQLiteDriver implements IDriver {
    private static final String TAG = "SQLiteDriver";

    @Override
    public void query(String name) {
        Log.e(TAG, name);
    }

    @Override
    public String toString() {
        return "SQLiteDriver{}";
    }
}
