package com.utu.utu;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class CustomButton extends AppCompatButton {
    public CustomButton(Context cv, AttributeSet asv, int nDefStyle)
    {
        super(cv, asv, nDefStyle);
        init(asv);
    }

    public CustomButton(Context cv, AttributeSet asv)
    {
        super(cv, asv);
        init(asv);
    }

    public CustomButton(Context cv)
    {
        super(cv);
        init(null);
    }

    private  void init(AttributeSet  asv)
    {
        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hanken-Book.ttf");
        setTypeface(customFont);
    }
}
