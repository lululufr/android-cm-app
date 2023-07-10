package com.cookmaster.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cookmaster.R;
import com.cookmaster.classes.Message;
import com.cookmaster.databinding.FragmentMessageBinding;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private FragmentMessageBinding binding;
    private ListView lv_message;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessageViewModel messageViewModel =
                new ViewModelProvider(this).get(MessageViewModel.class);

        binding = FragmentMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.lv_message = root.findViewById(R.id.lv_message);
        MessageAdapter messageAdapter = new MessageAdapter(getClassMessage(), getContext());
        this.lv_message.setAdapter(messageAdapter);
        this.lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message m = (Message)adapterView.getItemAtPosition(i);
                Toast.makeText(getContext(), m.getContent(), Toast.LENGTH_SHORT).show();
            }
        });

        final TextView textView = binding.textMessage;
        messageViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public List<Message> getClassMessage(){
        List<Message> result = new ArrayList<>();
        result.add(new Message("Karl","Alexis", "Salut ça va ?"));
        result.add(new Message("Alexis","Karl", "Ouais et toi bébou ?"));
        result.add(new Message("Karl","Alexis", "Je te gnok."));
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}