package com.example.gameapplication.userApp.utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class SegoeUiBoldItalic extends TextView {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public SegoeUiBoldItalic(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SegoeUiBoldItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public SegoeUiBoldItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Segoe UI Bold Italic.ttf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Segoe UI Bold Italic.ttf");
        super.setTypeface(tf);
    }
}