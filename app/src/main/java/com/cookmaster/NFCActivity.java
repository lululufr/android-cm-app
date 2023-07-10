package com.cookmaster;

import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NFCActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

    }
}
