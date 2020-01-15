package com.example.gameapplication.userApp.ui.battingclose;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BattingCloseFragmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BattingCloseFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}