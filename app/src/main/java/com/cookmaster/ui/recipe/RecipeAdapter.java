package com.cookmaster.ui.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookmaster.R;
import com.cookmaster.classes.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeAdapter extends BaseAdapter {

    private ArrayList<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(ArrayList<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.recipeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.item_recipe, null);
        }

        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_tags = view.findViewById(R.id.tv_tags);
        ImageView iv_food = view.findViewById(R.id.iv_food);

        Recipe current = (Recipe)getItem(i);

        tv_name.setText(current.getName());
        tv_tags.setText(current.tagsToString());
        if (!Objects.equals(current.getUrlImage(), "https://cookmaster.lululu.fr/storage/null")){
            Picasso.get().load(current.getUrlImage()).into(iv_food);
        }
        return view;

    }

    public void clear() {
        this.recipeList.clear();
        notifyDataSetChanged();
    }
}
