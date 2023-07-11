package com.cookmaster.ui.conversation.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookmaster.R;
import com.cookmaster.classes.Message;
import com.cookmaster.databinding.FragmentConversationBinding;
import com.cookmaster.databinding.FragmentMessageBinding;
import com.cookmaster.ui.conversation.ConversationAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageFragment extends Fragment {

    private FragmentMessageBinding binding;
    private ListView lv_conversation;

    private Button btn_send;

    private EditText et_message;

    private int idTo;
    private String nameTo;


    private static final String URL_GET = "https://cookmaster.lululu.fr/api/message/";
    private static final String URL_POST = "https://cookmaster.lululu.fr/api/message/";

    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle arguments = getArguments();
        idTo = arguments.getInt("toId", 0);
        nameTo = arguments.getString("toName", "");


        this.lv_conversation = root.findViewById(R.id.lv_message);
        this.et_message = root.findViewById(R.id.et_message);
        this.btn_send = root.findViewById(R.id.btn_send);

        getMessage();
        messageAdapter = new MessageAdapter(messageList, getContext());
        this.lv_conversation.setAdapter(messageAdapter);
        this.lv_conversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message m = (Message)adapterView.getItemAtPosition(i);
                Toast.makeText(getContext(), m.getContent(), Toast.LENGTH_SHORT).show();
            }
        });
        this.btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        return root;
    }



    public void getMessage(){
        RequestQueue file = Volley.newRequestQueue(requireActivity());
        Log.e("Test1", URL_GET + idTo);
        StringRequest r = new StringRequest(Request.Method.GET, URL_GET + idTo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    messageAdapter.clear();

                    JSONObject jso = new JSONObject(response);
                    String urlMessage = jso.toString();
                    Log.e("Test2", urlMessage);
                    JSONArray jsa = jso.getJSONArray("message");
                    for(int i = 0; i < jsa.length(); i++){
                        JSONObject jso2 = jsa.getJSONObject(i);
                        Message m = new Message(jso2.getString("username"), jso2.getInt("from_id"), jso2.getString("username"), jso2.getInt("to_id"), jso2.getString("content"), jso2.getString("created_at"));
                        messageList.add(m);
                    }
                    messageAdapter.notifyDataSetChanged();
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
        public Map<String, String> getParams() throws AuthFailureError {
            SharedPreferences savedIds = requireActivity().getSharedPreferences("savedIds", Context.MODE_PRIVATE);

            Map<String, String> params = new HashMap<>();
            params.put("from", savedIds.getString("id", null));
            params.put("to", "1");
            params.put("content", et_message.getText().toString());
            return params;
        }
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            SharedPreferences savedIds = requireActivity().getSharedPreferences("savedIds", Context.MODE_PRIVATE);

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + savedIds.getString("token", null));
            headers.put("Content-Type", "application/json");
            return headers;
        }
            ;   };

        file.add(r);
    }

    public void sendMessage(){
RequestQueue file = Volley.newRequestQueue(requireActivity());
        Log.e("Test1", URL_POST);
        StringRequest r = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jso = new JSONObject(response);
                    String urlMessage = jso.getString("message");
                    Log.e("Test2", urlMessage);
                    JSONArray jsa = jso.getJSONArray("message");
                    for(int i = 0; i < jsa.length(); i++){
                        JSONObject jso2 = jsa.getJSONObject(i);
                        Message m = new Message(jso2.getString("from_name"), jso2.getInt("from_id"), jso2.getString("to_name"), jso2.getInt("to_id"), jso2.getString("content"), jso2.getString("created_at"));
                        messageList.add(m);
                    }
                    messageAdapter.notifyDataSetChanged();
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