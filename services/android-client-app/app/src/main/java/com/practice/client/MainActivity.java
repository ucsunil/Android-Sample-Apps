package com.practice.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RemoteServiceConnection serviceConnection;
    private Messenger messenger;

    private boolean isBound = false;

    private Button bind;
    private Button unbind;
    private Button send;
    private EditText message_what;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceConnection = new RemoteServiceConnection(this);
        bind = (Button) findViewById(R.id.bind);
        unbind = (Button) findViewById(R.id.unbind);
        send = (Button) findViewById( R.id.send);
        message_what = (EditText) findViewById(R.id.message_what);

        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isBound) {
                    Intent intent = new Intent();
                    intent.setClassName("com.practice.remote", "com.practice.remote.services.ProtectedRemoteService");
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                    isBound = true;
                } else {
                    Log.d(TAG, "Already bound to remote service");
                    Toast.makeText(MainActivity.this, "Already bound to remote service!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound) {
                    unbindService(serviceConnection);
                    isBound = false;
                } else {
                    Log.d(TAG, "Not bound to remote service");
                    Toast.makeText(MainActivity.this, "Not bound to remote service!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound && serviceConnection.getIBinder() != null) {
                    messenger = new Messenger(serviceConnection.getIBinder());
                    int messageToSend = Integer.valueOf(message_what.getText().toString());
                    Message message = Message.obtain(null, messageToSend, 0, 0);
                    try {
                        messenger.send(message);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!isBound) {
            Intent intent = new Intent();
            intent.setClassName("com.practice.remote", "com.practice.remote.services.ProtectedRemoteService");
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    private static class RemoteServiceConnection implements ServiceConnection {

        private Context context;
        private IBinder iBinder;

        public RemoteServiceConnection(Context context) {
            this.context = context;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Toast.makeText(context, "Connected to remote service", Toast.LENGTH_SHORT).show();
            this.iBinder = iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(context, "Disconnected from remote service", Toast.LENGTH_SHORT).show();
            this.iBinder = null;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Toast.makeText(context, "Binding to remote service died", Toast.LENGTH_SHORT).show();
            this.iBinder = null;
        }

        public IBinder getIBinder() {
            return this.iBinder;
        }

    }
}
