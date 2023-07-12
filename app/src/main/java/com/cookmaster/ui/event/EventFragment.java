package com.cookmaster.ui.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.cookmaster.classes.Event;
import com.cookmaster.classes.Ingredient;
import com.cookmaster.classes.Recipe;
import com.cookmaster.databinding.FragmentEventBinding;
import com.cookmaster.ui.event.openevent.OpenEventFragment;
import com.cookmaster.ui.recipe.RecipeAdapter;
import com.cookmaster.ui.recipe.openrecipe.OpenRecipeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventFragment extends Fragment {

    private FragmentEventBinding binding;

    private EventAdapter eventAdapter;

    private ListView lv_event;

    private ArrayList<Event> eventArrayList = new ArrayList<>();

    private static final String URL = "https://cookmaster.lululu.fr/api/event/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getRecipe();
        this.lv_event = root.findViewById(R.id.lv_event);
        eventAdapter = new EventAdapter(eventArrayList, getContext());
        this.lv_event.setAdapter(eventAdapter);
        this.lv_event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OpenEventFragment openEventFragment = new OpenEventFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("json", eventArrayList.get(i).eventToJson());
                openEventFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, openEventFragment);
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
                    eventAdapter.clear();

                    JSONObject jso = new JSONObject(response);
                    String urlEvents = jso.getString("events");
                    Log.e("Test2", urlEvents);

                    JSONArray events = jso.getJSONArray("events");
                    for(int i = 0; i < events.length(); i++){
                        JSONObject event = events.getJSONObject(i);

                        JSONObject roomsJson = event.getJSONObject("rooms");
                        String adresse = roomsJson.getString("street") + " " + roomsJson.getString("city") + " " + roomsJson.getString("postal_code");

                        JSONObject recipeJson = event.getJSONObject("recipes");


                        eventArrayList.add(new Event(event.getString("title"),event.getString("description"), event.getString("start"), event.getString("chef_username"), adresse, event.getInt("isParticipating") == 1, recipeJson.getString("title")));
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
        eventAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}