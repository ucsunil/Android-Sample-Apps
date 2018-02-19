package com.practice.chbubble;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sudavid on 2/7/18.
 */

public class MessageAdapter extends ArrayAdapter<ChatBubble> {

    private Context context;
    private List<ChatBubble> list;

    public MessageAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ChatBubble> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutResource = 0;
        ChatBubble chatBubble = getItem(position);
        if(chatBubble.isMyMessage()) {
            layoutResource = R.layout.left_chat_bubble;
        } else {
            layoutResource = R.layout.right_chat_bubble;
        }
        ViewHolder holder;
        if(convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        // Set message content
        holder.message.setText(chatBubble.getMessage());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    private class ViewHolder {
        TextView message;

        public ViewHolder(View v) {
            message = (TextView) v. findViewById(R.id.txt_msg);
        }
    }
}
