package com.cookmaster.ui.conversation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookmaster.R;
import com.cookmaster.classes.Conversation;
import com.cookmaster.classes.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConversationAdapter extends BaseAdapter {

    private ArrayList<Conversation> conversationArrayList;
    private Context context;

    public ConversationAdapter(ArrayList<Conversation> conversationArrayList, Context context) {
        this.conversationArrayList = conversationArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.conversationArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.conversationArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.item_conversation, null);
        }
        TextView tv_from = view.findViewById(R.id.tv_from);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);

        Conversation current = (Conversation)getItem(i);

        tv_from.setText(current.getToName());
        if (current.getUrlImage() != null){
            Picasso.get().load(current.getUrlImage()).into(iv_avatar);
        }

        return view;

    }
    public void clear() {
        this.conversationArrayList.clear();
        notifyDataSetChanged();
    }
}
