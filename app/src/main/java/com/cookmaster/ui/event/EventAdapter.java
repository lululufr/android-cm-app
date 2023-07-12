package com.cookmaster.ui.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cookmaster.R;
import com.cookmaster.classes.Event;
import com.cookmaster.classes.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {

    private ArrayList<Event> eventArrayList;
    private Context context;

    public EventAdapter(ArrayList<Event> eventArrayList, Context context) {
        this.eventArrayList = eventArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.eventArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.eventArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.item_event, null);
        }

        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_location = view.findViewById(R.id.tv_location);
        TextView tv_inscrit = view.findViewById(R.id.tv_inscrit);

        Event current = (Event)getItem(i);

        tv_name.setText(current.getName());
        tv_date.setText(current.getDate());
        tv_location.setText(current.getLocation());
        tv_inscrit.setText(current.getInscrit() ? "Inscrit" : "Non inscrit");
        tv_inscrit.setTextColor(current.getInscrit() ? ContextCompat.getColor(context, R.color.green) : ContextCompat.getColor(context, R.color.red));


        return view;

    }

    public void clear() {
        this.eventArrayList.clear();
        notifyDataSetChanged();
    }
}
