package com.example.sorteroyale.ui.conta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ContaViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
