package com.cookmaster.ui.nfc;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cookmaster.R;
import com.cookmaster.databinding.FragmentNfcBinding;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class NfcFragment extends Fragment implements NfcAdapter.ReaderCallback {

    private NfcAdapter nfcAdapter;
    private Button btnread;
    private Button btnwrite;
    private TextView textView;

    private ImageView ivGame;
    private int actionType = 0;

    private FragmentNfcBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NfcViewModel dashboardViewModel =
                new ViewModelProvider(this).get(NfcViewModel.class);

        binding = FragmentNfcBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext());
        btnread = root.findViewById(R.id.btnread);
        btnwrite = root.findViewById(R.id.btnwrite);
        textView = root.findViewById(R.id.text_nfc);
        ivGame = root.findViewById(R.id.ivGame);

        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionType = 1;
                textView.setText("Recherche du tag NFC en cours...");
                enableNfcDetection();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Désactiver la détection du tag NFC après 5 secondes
                        disableNfcDetection();
                        if (textView.getText().toString().equals("Recherche du tag NFC en cours...")) {
                            updateTextView("Aucun NFC détecté.");
                        }
                        actionType = 0;
                    }
                }, 5000); // Attendre 5 secondes (5000 millisecondes)
            }
        });

        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionType = 2;
                textView.setText("Recherche du tag NFC en cours...");
                enableNfcDetection();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Désactiver la détection du tag NFC après 5 secondes
                        disableNfcDetection();
                        if (textView.getText().toString().equals("Recherche du tag NFC en cours...")) {
                            updateTextView("Aucun NFC détecté.");
                        }
                        actionType = 0;
                    }
                }, 5000); // Attendre 5 secondes (5000 millisecondes)
            }
        });

        final TextView textView = binding.textNfc;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void disableNfcDetection() {
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            nfcAdapter.disableReaderMode(requireActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        enableNfcDetection();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableNfcDetection();
    }

    private void enableNfcDetection() {
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            nfcAdapter.enableReaderMode(requireActivity(), this, NfcAdapter.FLAG_READER_NFC_A, null);
        }
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        if (actionType == 1) {
            NfcA nfcA = NfcA.get(tag);
            if (nfcA != null) {
                try {
                    nfcA.connect();

                    int page = 7;

                    // Lire la page spécifiée du tag
                    byte[] readCommand = {(byte) 0x30, (byte) page};
                    byte[] readResponse = nfcA.transceive(readCommand);

                    // Vérifier la réponse
                    if (readResponse != null && readResponse.length >= 4) {
                        byte[] payload = Arrays.copyOfRange(readResponse, 0, 4); // Récupérer les données utiles

                        // Convertir les données en chaîne de caractères UTF-8
                        String data = new String(payload, StandardCharsets.UTF_8);

                        switch(data){
                            case "0000":
                                updateTextView("Dommage, vous avez perdu...");
                                ivGame.setImageResource(R.drawable.sad);
                                break;
                            case "3009":
                                updateTextView("Alexis le PDG vous gratifie d'une alternance payée au SMIC !");
                                ivGame.setImageResource(R.drawable.alexis);
                                break;
                            case "1661":
                                updateTextView("Un dîner en tête avec M.SANANES à déguster du bon champagne !");
                                ivGame.setImageResource(R.drawable.sananes);
                                break;
                            case "1234":
                                updateTextView("Une pizza avec tous les étudiants de la 2A4 !");
                                ivGame.setImageResource(R.drawable.pizza);
                                break;
                            case "2324":
                                updateTextView("Vous avez gagné un voyage à Toulouse pour 2 personnes !");
                                ivGame.setImageResource(R.drawable.toulouse);
                                break;
                                default:
                                    updateTextView("Données non reconnues");
                        }
                    } else {
                        // Aucune donnée lue
                        updateTextView("Aucune donnée lue");
                    }

                    // Fermer la connexion avec le tag
                    nfcA.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Le tag NFC ne prend pas en charge la technologie NFC-A
                updateTextView("Tag NFC incompatible");
            }
        } else if (actionType == 2) {
            NfcA nfcA = NfcA.get(tag);
            if (nfcA != null) {
                try {
                    Log.e("NFC", "I'm in");
                    nfcA.connect();
                    // Remplir les octets restants avec des zéros si nécessaire
                    byte[] payload = new byte[7];
                    byte[] colorBytes = generateCode();
                    System.arraycopy(colorBytes, 0, payload, 0, colorBytes.length);

                    // Écrire les données sur la page spécifiée du tag
                    byte[] writeCommand = {(byte) 0xA2, (byte) 7, payload[0], payload[1], payload[2], payload[3]}; // Commande d'écriture
                    byte[] writeResponse = nfcA.transceive(writeCommand);

                    // Vérifier la réponse
                    if (writeResponse != null && writeResponse.length > 0 && (writeResponse[0] & 0x0F) == 0x0A) {
                        updateTextView("Écriture réussie");
                    } else {
                        updateTextView("Échec de l'écriture");
                    }
                    // Fermer la connexion avec le tag
                    nfcA.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else{
                // Le tag NFC ne prend pas en charge la technologie NFC-A
                updateTextView("Tag NFC incompatible");
            }
        }
    }




    private void updateTextView(String text) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
private byte[] generateCode() {
    Random random = new Random();
    int game = random.nextInt(5);
    switch(game){
        case 0:
            return "0000".getBytes(StandardCharsets.UTF_8);
        case 1:
            return "3009".getBytes(StandardCharsets.UTF_8);
        case 2:
            return "1661".getBytes(StandardCharsets.UTF_8);
        case 3:
            return "1234".getBytes(StandardCharsets.UTF_8);
        case 4:
            return "2324".getBytes(StandardCharsets.UTF_8);
    }
    return "9999".getBytes(StandardCharsets.UTF_8);
}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}