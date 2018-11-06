package com.confirmu.up.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by admin on 08-Nov-17.
 */

public class BoldTextView extends android.support.v7.widget.AppCompatTextView {
    public BoldTextView(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/lato_bold.ttf");
        this.setTypeface(type);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/lato_bold.ttf");
        this.setTypeface(type);
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/lato_bold.ttf");
        this.setTypeface(type);
    }
}
