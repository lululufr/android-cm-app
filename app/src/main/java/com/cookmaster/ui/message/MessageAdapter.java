package com.cookmaster.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookmaster.R;
import com.cookmaster.classes.Message;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private List<Message> messageList;
    private Context context;

    public MessageAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.item_message, null);
        }
        TextView tv_from = view.findViewById(R.id.tv_from);
        TextView tv_to = view.findViewById(R.id.tv_to);
        TextView tv_content = view.findViewById(R.id.tv_content);

        Message current = (Message)getItem(i);

        tv_from.setText(current.getFrom());
        tv_to.setText(current.getTo());
        tv_content.setText(current.getContent());

        return view;

    }
}
