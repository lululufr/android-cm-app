package com.cookmaster.ui.recipe.openrecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cookmaster.R;
import com.cookmaster.classes.Ingredient;
import com.cookmaster.classes.Recipe;
import com.cookmaster.databinding.FragmentOpenRecipeBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OpenRecipeFragment extends Fragment {

    private FragmentOpenRecipeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOpenRecipeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        TextView tv_recipe_name = root.findViewById(R.id.tv_name);
        TextView tv_description = root.findViewById(R.id.tv_description);
        TextView tv_ingredient = root.findViewById(R.id.tv_recette);
        TextView tv_recipe_tags = root.findViewById(R.id.tv_recipe_tags);
        ImageView iv_food_image = root.findViewById(R.id.iv_food_image);


        Bundle arguments = getArguments();
        try {
            JSONObject jsoRecipe = new JSONObject(arguments.getString("json", null));

            ArrayList<Ingredient> ingredients = new ArrayList<>();
            JSONArray jsaIngredients = jsoRecipe.getJSONArray("ingredients");
            for (int i = 0; i < jsaIngredients.length(); i++) {
                JSONObject jsoIngredient = jsaIngredients.getJSONObject(i);
                if (jsoIngredient.getString("unit").equals("null"))
                    ingredients.add(new Ingredient(jsoIngredient.getString("name"), jsoIngredient.getInt("quantity")));
                else
                    ingredients.add(new Ingredient(jsoIngredient.getString("name"), jsoIngredient.getInt("quantity"), jsoIngredient.getString("unit")));
            }

            ArrayList<String> tags = new ArrayList<>();
            JSONArray jsaTags = jsoRecipe.getJSONArray("tags");
            for (int i = 0; i < jsaTags.length(); i++) {
                tags.add(jsaTags.getString(i));
            }
            Recipe recipe = new Recipe(jsoRecipe.getString("name"), ingredients, tags, jsoRecipe.getString("description"), jsoRecipe.getString("urlImage"));

            tv_recipe_name.setText(recipe.getName());
            tv_description.setText(recipe.getDescription());
            tv_ingredient.setText(recipe.ingredientsToString());
            tv_recipe_tags.setText(recipe.tagsToString());
            Picasso.get().load(recipe.getUrlImage()).into(iv_food_image);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}