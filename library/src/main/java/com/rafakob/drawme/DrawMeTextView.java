package com.rafakob.drawme;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rafakob.drawme.delegate.DrawMe;
import com.rafakob.drawme.delegate.DrawMeShapeText;

public class DrawMeTextView extends TextView {
    private final DrawMe drawMe;

    public DrawMeTextView(Context context) {
        super(context);
        drawMe = new DrawMeShapeText(context, this, null);
    }

    public DrawMeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawMe = new DrawMeShapeText(context, this, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] size = drawMe.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(size[0], size[1]);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        drawMe.onLayout(changed, left, top, right, bottom);
    }
}
