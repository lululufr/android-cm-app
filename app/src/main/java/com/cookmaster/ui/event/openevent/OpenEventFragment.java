package com.cookmaster.ui.event.openevent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cookmaster.R;
import com.cookmaster.classes.Event;
import com.cookmaster.databinding.FragmentOpenEventBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class OpenEventFragment extends Fragment {

    private FragmentOpenEventBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOpenEventBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        TextView tv_event_name = root.findViewById(R.id.tv_event_name);
        TextView tv_description = root.findViewById(R.id.tv_recette);
        TextView tv_chef = root.findViewById(R.id.tv_chef);
        TextView tv_recette = root.findViewById(R.id.tv_recette);
        TextView tv_inscrit_open = root.findViewById(R.id.tv_inscrit_open);


        Bundle arguments = getArguments();
        try {
            JSONObject jsoEvent = new JSONObject(arguments.getString("json", null));
            Event event = new Event(jsoEvent.getString("name"), jsoEvent.getString("description"), jsoEvent.getString("date"), jsoEvent.getString("chef_username"), jsoEvent.getString("location"), jsoEvent.getString("inscrit").equals("true"), jsoEvent.getString("recipe_name"));

            tv_event_name.setText(event.getName());
            tv_description.setText(event.getDescription());
            tv_chef.setText("Chef : " + event.getChefUsername());
            tv_recette.setText("Recette cuisinée : " + event.getRecipeName());
            if(event.getInscrit()) {
                tv_inscrit_open.setText("Vous êtes inscrit à cet évènement !");
                ContextCompat.getColor(getContext(), R.color.green);
            }
            else {
                tv_inscrit_open.setText("Vous n'êtes pas inscrit à cet évènement.");
                ContextCompat.getColor(getContext(), R.color.red);
            }

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