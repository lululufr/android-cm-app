package com.cookmaster.ui.event.openevent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cookmaster.R;
import com.cookmaster.classes.Event;
import com.cookmaster.classes.Recipe;
import com.cookmaster.databinding.FragmentOpenEventBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class OpenEventFragment extends Fragment {

    private FragmentOpenEventBinding binding;

    private TextView tv_event_name;
    private TextView tv_description;
    private TextView tv_chef;
    private TextView tv_recette;
    private TextView tv_inscrit_open;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOpenEventBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        this.tv_event_name = root.findViewById(R.id.tv_event_name);
        this.tv_description = root.findViewById(R.id.tv_recette);
        this.tv_chef = root.findViewById(R.id.tv_chef);
        this.tv_recette = root.findViewById(R.id.tv_recette);
        this.tv_inscrit_open = root.findViewById(R.id.tv_inscrit_open);


        Bundle arguments = getArguments();
        try {
            Log.e("JSON", arguments.getString("json", null));
            JSONObject jsoEvent = new JSONObject(arguments.getString("json", null));
            Event event = new Event(jsoEvent.getString("name"), jsoEvent.getString("description"), jsoEvent.getString("date"), jsoEvent.getString("chef_username"), jsoEvent.getString("location"), jsoEvent.getString("inscrit").equals("1"), jsoEvent.getString("recipe_name"));

            Log.e("Event", event.getName());
            this.tv_event_name.setText(event.getName());
            this.tv_description.setText(event.getDescription());
            this.tv_chef.setText(event.getChefUsername());
            this.tv_recette.setText(event.getRecipeName());
            if(event.getInscrit()) {
                this.tv_inscrit_open.setText("Vous êtes inscrit à cet évènement");
            }
            else {
                this.tv_inscrit_open.setText("Vous n'êtes pas inscrit à cet évènement");
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