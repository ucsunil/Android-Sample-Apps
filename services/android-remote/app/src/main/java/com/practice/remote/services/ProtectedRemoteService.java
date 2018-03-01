package com.practice.remote.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sudavid on 2/20/18.
 */

public class ProtectedRemoteService extends Service {

    private static final String TAG = "ProtectedRemoteService";

    private Messenger messenger = new Messenger(new MessageHandler(ProtectedRemoteService.this));


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Received call to bind from remote client");
        return messenger.getBinder();
    }

    private static class MessageHandler extends Handler {

        private Context context;

        public MessageHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message message) {
            Log.d(TAG, "Received message; message.what: " + message.what);
            Toast.makeText(context, "message.what: " + message.what, Toast.LENGTH_SHORT).show();
        }
    }
}
