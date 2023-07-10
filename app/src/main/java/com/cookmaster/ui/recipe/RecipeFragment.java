package com.cookmaster.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookmaster.R;
import com.cookmaster.classes.Ingredient;
import com.cookmaster.classes.Recipe;
import com.cookmaster.databinding.FragmentRecipeBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding binding;
    private ListView lv_recipe;
    private final ArrayList<Recipe> recipesList = new ArrayList<>();

    private RecipeAdapter recipeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecipeViewModel homeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getRecipe();

        this.lv_recipe = root.findViewById(R.id.lv_recipe);
        recipeAdapter = new RecipeAdapter(recipesList, getContext());
        Log.e("Test3", "Ici");
        this.lv_recipe.setAdapter(recipeAdapter);
        this.lv_recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe m = (Recipe)adapterView.getItemAtPosition(i);
                Toast.makeText(getContext(), m.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        final TextView textView = binding.textRecipe;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public ArrayList<Recipe> getClassRecipe(){
        ArrayList<Recipe> result = new ArrayList<>();

        ArrayList<Ingredient> ingredientsRougail = new ArrayList<>();
        ingredientsRougail.add(new Ingredient("Riz", 200, "grammes"));
        ingredientsRougail.add(new Ingredient("Saucisse", 2));
        ingredientsRougail.add(new Ingredient("Haricots rouges", 50, "grammes"));

        ArrayList<String> tagList = new ArrayList<>();
        tagList.add((new String("Exotique")));

        result.add(new Recipe("Rougail saucisse",ingredientsRougail, tagList, "Le bon rougail de maman", "https://blog.brithotel.fr/wp-content/uploads/2021/02/rougail-saucisse2-1200x710.jpg"));
        return result;
    }

    public void getRecipe() {
        RequestQueue file = Volley.newRequestQueue(binding.getRoot().getContext());
        String url = "https://cookmaster.lululu.fr/api/recipe/";
        Log.e("Test1", url);

        StringRequest r = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recipeAdapter.clear();

                    JSONObject jso = new JSONObject(response);
                    String urlRecipes = jso.getString("recipes");
                    Log.e("Test2", urlRecipes);

                    JSONArray recipes = jso.getJSONArray("recipes");
                    for(int i = 0; i < recipes.length(); i++){
                        JSONObject recipe = recipes.getJSONObject(i);

                        ArrayList<Ingredient> ingredients = new ArrayList<>();
                        JSONArray ingredientsJson = recipe.getJSONArray("ingredients");
                        for(int j = 0; j < ingredientsJson.length(); j++){
                            JSONObject ingredient = ingredientsJson.getJSONObject(j);
                            ingredients.add(new Ingredient(ingredient.getString("ingredients_name"), ingredient.getInt("amount"), ingredient.getString("unit")));
                        }

                        ArrayList<String> tags = new ArrayList<>();
                        JSONArray tagsJson = recipe.getJSONArray("tags");
                        for(int j = 0; j < tagsJson.length(); j++){
                            JSONObject tag = tagsJson.getJSONObject(j);
                            tags.add(tag.getString("tag_name"));
                        }

                        recipesList.add(new Recipe(recipe.getString("title"), ingredients, tags,recipe.getString("content"), "https://cookmaster.lululu.fr/storage/" + recipe.getString("image")));
                        updateListView();
                    }

                }catch(Exception e){
                    Log.e("Test2", "OOOOOAAAAAA");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Test2", "Erreur");
            }
        });
        file.add(r);
    }
    private void updateListView() {
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}