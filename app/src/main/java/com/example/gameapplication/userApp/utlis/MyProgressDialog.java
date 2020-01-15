package com.example.gameapplication.userApp.utlis;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog extends ProgressDialog {
    public MyProgressDialog(Context context) {
        super(context);

        setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setMessage("Please wait......");
        setCancelable(false);
    }
}