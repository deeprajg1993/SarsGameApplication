package com.example.gameapplication.userApp.utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class SegoeUIBold extends TextView {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public SegoeUIBold(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SegoeUIBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public SegoeUIBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Segoe UI Bold.ttf");
        this.setTypeface(font);
    }
    @Override
    public void setTypeface(Typeface tf) {
        tf= Typeface.createFromAsset(getContext().getAssets(), "fonts/Segoe UI Bold.ttf");
        super.setTypeface(tf);
    }
}