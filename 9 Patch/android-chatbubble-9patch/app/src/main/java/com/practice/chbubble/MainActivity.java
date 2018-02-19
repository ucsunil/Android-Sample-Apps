package com.practice.chbubble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button send;
    private EditText text;

    private boolean isMine;
    private List<ChatBubble> chatBubbles;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatBubbles = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_msg);
        send = (Button) findViewById(R.id.chat_send);
        text = (EditText) findViewById(R.id.message);

        messageAdapter = new MessageAdapter(this, R.layout.left_chat_bubble, chatBubbles);
        listView.setAdapter(messageAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Set some text message", Toast.LENGTH_SHORT);
                } else {
                    ChatBubble chatBubble = new ChatBubble(text.getText().toString().trim(), isMine);
                    chatBubbles.add(chatBubble);
                    messageAdapter.notifyDataSetChanged();
                    if(!isMine) {
                        isMine = true;
                    } else {
                        isMine = false;
                    }
                }
            }
        });

    }
}
