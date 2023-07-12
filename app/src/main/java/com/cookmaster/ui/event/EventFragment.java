package com.cookmaster.ui.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookmaster.R;
import com.cookmaster.classes.Event;
import com.cookmaster.databinding.FragmentEventBinding;
import com.cookmaster.ui.event.openevent.OpenEventFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventFragment extends Fragment {

    private FragmentEventBinding binding;

    private EventAdapter eventAdapter;

    private final ArrayList<Event> eventArrayList = new ArrayList<>();

    private static final String URL = "https://cookmaster.lululu.fr/api/event/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getRecipe();
        ListView lv_event = root.findViewById(R.id.lv_event);
        eventAdapter = new EventAdapter(eventArrayList, getContext());
        lv_event.setAdapter(eventAdapter);
        lv_event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OpenEventFragment openEventFragment = new OpenEventFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
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

        StringRequest r = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    eventAdapter.clear();

                    JSONObject jso = new JSONObject(response);

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
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        })
        {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            SharedPreferences savedIds = requireActivity().getSharedPreferences("savedIds", Context.MODE_PRIVATE);

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