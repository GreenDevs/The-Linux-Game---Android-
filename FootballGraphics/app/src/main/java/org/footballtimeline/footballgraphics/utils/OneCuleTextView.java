package org.footballtimeline.footballgraphics.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OneCuleTextView extends TextView {

    public OneCuleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public OneCuleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OneCuleTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "calibri.ttf");
        setTypeface(tf);
    }

}