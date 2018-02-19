package com.practice.chbubble;

/**
 * Created by sudavid on 2/6/18.
 */

public class ChatBubble {

    private String message;
    private boolean myMessage;

    public ChatBubble(String message, boolean myMessage) {
        this.message = message;
        this.myMessage = myMessage;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMyMessage() {
        return myMessage;
    }
}
