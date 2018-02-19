package com.android.customasync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DemoAsyncTask().execute();
    }

    private class DemoAsyncTask extends CustomAsyncTask<Void, Void, Void> {

        private static final String TAG = "DemoAsyncTask";

        @Override
        public void onPreExecute() {
            Log.d(TAG, "onPreExecute() -> Printing from thread: " + Thread.currentThread().getName());
        }

        @Override
        public Void doInBackground(Void... input) {
            Log.d(TAG, "doInBackground() -> Printing from thread: " + Thread.currentThread().getName());
            publishProgress();
            return null;
        }

        @Override
        public void onProgressUpdate(Void... voids) {
            Log.d(TAG, "onProgressUpdate() -> Printing from thread: " + Thread.currentThread().getName());
        }

        @Override
        public void onPostExecute(Void output) {
            Log.d(TAG, "onPostExecute() -> Printing from thread: " + Thread.currentThread().getName());
        }
    }
}
