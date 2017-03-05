package org.footballtimeline.footballgraphics.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OneCuleThinTextView extends TextView {

    public OneCuleThinTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public OneCuleThinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OneCuleThinTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "a.ttf");
        setTypeface(tf);
    }

}