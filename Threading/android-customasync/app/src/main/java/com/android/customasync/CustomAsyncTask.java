package com.android.customasync;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by sudavid on 1/31/18.
 */

public abstract class CustomAsyncTask<I, P, O> extends Thread {

    private Handler mainHandler;
    private I[] input;

    public void execute(I... input) {
        this.input = input;
        mainHandler = new Handler(Looper.getMainLooper());
        start();
    }

    public void onPreExecute() {

    }

    public abstract O doInBackground(I... input);

    public void publishProgress(final P... progress) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(progress);
            }
        });
    }

    public void onProgressUpdate(P... progress) {

    }

    public void onPostExecute(O output) {

    }

    @Override
    public void run() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                onPreExecute();
            }
        });
        final O output = doInBackground(input);
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(output);
            }
        });
    }
}
