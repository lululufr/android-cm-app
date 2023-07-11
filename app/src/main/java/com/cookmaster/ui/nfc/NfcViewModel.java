package com.cookmaster.ui.nfc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NfcViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NfcViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Bienvenue dans l'onglet NFC ! Qu'allez vous gagner aujourd'hui ?");
    }

    public LiveData<String> getText() {
        return mText;
    }
}