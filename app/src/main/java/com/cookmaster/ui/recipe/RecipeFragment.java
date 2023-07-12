package com.cookmaster.ui.recipe;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
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
import com.cookmaster.ui.conversation.message.MessageFragment;
import com.cookmaster.ui.recipe.openrecipe.OpenRecipeFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding binding;
    private ListView lv_recipe;
    private final ArrayList<Recipe> recipesList = new ArrayList<>();

    private static final String URL = "https://cookmaster.lululu.fr/api/recipe/";

    private RecipeAdapter recipeAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getRecipe();

        this.lv_recipe = root.findViewById(R.id.lv_recipe);
        recipeAdapter = new RecipeAdapter(recipesList, getContext());
        this.lv_recipe.setAdapter(recipeAdapter);
        this.lv_recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OpenRecipeFragment openRecipeFragment = new OpenRecipeFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("json", recipesList.get(i).recipeToJson());
                openRecipeFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, openRecipeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    public void getRecipe() {
        RequestQueue file = Volley.newRequestQueue(requireActivity());
        Log.e("Test1", URL);

        StringRequest r = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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
                            if (ingredient.getString("unit").equals("null"))
                                ingredients.add(new Ingredient(ingredient.getString("ingredients_name"), ingredient.getInt("amount")));
                            else {
                                ingredients.add(new Ingredient(ingredient.getString("ingredients_name"), ingredient.getInt("amount"), ingredient.getString("unit")));
                            }
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

        })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            SharedPreferences savedIds = requireActivity().getSharedPreferences("savedIds", Context.MODE_PRIVATE);
            Log.e("Test2", savedIds.getString("token", null));

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + savedIds.getString("token", null));
            headers.put("Content-Type", "application/json");
            return headers;
        }
        };

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