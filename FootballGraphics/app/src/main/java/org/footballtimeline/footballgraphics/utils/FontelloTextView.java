package org.footballtimeline.footballgraphics.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontelloTextView extends TextView {

    public FontelloTextView ( Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontelloTextView ( Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontelloTextView ( Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fontello.ttf");
        setTypeface(tf);
    }

}