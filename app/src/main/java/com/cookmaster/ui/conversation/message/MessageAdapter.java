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

    private final ArrayList<Message> messageArrayList;
    private final Context context;

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
        TextView tv_from_left = view.findViewById(R.id.tv_from_left);
        TextView tv_content_left = view.findViewById(R.id.tv_content_left);

        TextView tv_from_right = view.findViewById(R.id.tv_from_right);
        TextView tv_content_right = view.findViewById(R.id.tv_content_right);

        Message current = (Message)getItem(i);
        SharedPreferences savedIds = context.getSharedPreferences("savedIds", Context.MODE_PRIVATE);
        if (current.getFromId() == savedIds.getInt("id", 0)){
            tv_content_right.setVisibility(View.VISIBLE);
            tv_from_right.setVisibility(View.VISIBLE);
            tv_from_right.setText("Vous");
            tv_content_right.setText(current.getContent());
            tv_content_right.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tv_from_right.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tv_from_left.setVisibility(View.GONE);
            tv_content_left.setVisibility(View.GONE);
        }
        else {
            tv_from_left.setVisibility(View.VISIBLE);
            tv_content_left.setVisibility(View.VISIBLE);
            tv_from_left.setText(current.getFromName());
            tv_content_left.setText(current.getContent());
            tv_content_left.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tv_from_left.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tv_from_right.setVisibility(View.GONE);
            tv_content_right.setVisibility(View.GONE);
        }

        return view;

    }

    public void clear() {
        this.messageArrayList.clear();
        notifyDataSetChanged();

    }
}
