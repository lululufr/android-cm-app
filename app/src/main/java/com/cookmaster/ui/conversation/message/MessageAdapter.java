package com.cookmaster.ui.conversation.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookmaster.R;
import com.cookmaster.classes.Message;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {

    private ArrayList<Message> messageArrayList;
    private Context context;

    public MessageAdapter(ArrayList<Message> messageArrayList, Context context) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return this.messageArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.messageArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.item_message, null);
        }
        TextView tv_from = view.findViewById(R.id.tv_from);
        TextView tv_content = view.findViewById(R.id.tv_content);

        Message current = (Message)getItem(i);
        SharedPreferences savedIds = context.getSharedPreferences("savedIds", Context.MODE_PRIVATE);

        tv_from.setText(current.getFromId() == savedIds.getInt("id", 0) ? "Vous" : current.getFromName());
        tv_content.setText(current.getContent());

        return view;

    }

    public void clear() {
        this.messageArrayList.clear();
        notifyDataSetChanged();

    }
}
