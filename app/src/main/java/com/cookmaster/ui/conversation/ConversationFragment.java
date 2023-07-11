package com.cookmaster.ui.conversation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.cookmaster.classes.Conversation;
import com.cookmaster.databinding.FragmentConversationBinding;
import com.cookmaster.ui.conversation.ConversationAdapter;
import com.cookmaster.ui.conversation.message.MessageFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConversationFragment extends Fragment {

    private FragmentConversationBinding binding;
    private ListView lv_conversation;
    private ConversationAdapter conversationAdapter;

    private ArrayList<Conversation> conversationList = new ArrayList<>();

    private static final String URL = "https://cookmaster.lululu.fr/api/conversation/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConversationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.lv_conversation = root.findViewById(R.id.lv_message);
        getConversation();
        conversationAdapter = new ConversationAdapter(conversationList, getContext());
        this.lv_conversation.setAdapter(conversationAdapter);
        this.lv_conversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MessageFragment messageFragment = new MessageFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("toId", conversationList.get(i).getToId());
                bundle.putString("toName", conversationList.get(i).getToName());
                messageFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, messageFragment);
                fragmentTransaction.commit();
            }
        });
        return root;
    }


    public void getConversation(){
        RequestQueue file = Volley.newRequestQueue(requireActivity());
        Log.e("Test1", URL);
        StringRequest r = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    conversationAdapter.clear();

                    JSONObject jso = new JSONObject(response);
                    String urlConversation = jso.getString("convs");
                    Log.e("TestResponse", urlConversation);
                    JSONArray jsa = jso.getJSONArray("convs");
                    for (int i = 0; i < jsa.length(); i++) {
                        JSONObject js = jsa.getJSONObject(i);
                        String toName = js.getString("username");
                        String toAvatar = js.getString("profil_picture");
                        int toId = js.getInt("id");
                        conversationList.add(new Conversation(toName, toId, "https://cookmaster.lululu.fr/storage/" + toAvatar));
                    }
                    conversationAdapter.notifyDataSetChanged();
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
            ;   };

        file.add(r);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}