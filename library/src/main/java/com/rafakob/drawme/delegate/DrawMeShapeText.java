package com.rafakob.drawme.delegate;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.rafakob.drawme.R;
import com.rafakob.drawme.util.FontCache;

public class DrawMeShapeText extends DrawMeShape {
    protected String font;
    protected int textColor;
    protected int textColorPressed;
    protected int textColorDisabled;

    public DrawMeShapeText(Context context, View view, AttributeSet attrs) {
        super(context, view, attrs);
    }

    protected void obtainTextAttributes(TypedArray a) {
        font = a.getString(R.styleable.DrawMeText_font);
        textColor = a.getColor(R.styleable.DrawMeText_textColor, ((TextView) mView).getTextColors().getDefaultColor());
        textColorPressed = a.getColor(R.styleable.DrawMeText_textColorPressed, textColor);
        textColorDisabled = a.getColor(R.styleable.DrawMeText_textColorDisabled, textColor);
    }

    @Override
    public void obtainAttributes(Context context, AttributeSet attrs) {
        super.obtainAttributes(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawMeText);
        obtainTextAttributes(typedArray);
        typedArray.recycle();

        if (!TextUtils.isEmpty(font))
            applyFont();
    }

    private void applyFont() {
        ((TextView) mView).setTypeface(FontCache.get(mView.getContext(), font));
    }

    @Override
    public void updateLayout() {
        super.updateLayout();
        if (textColorPressed != Integer.MAX_VALUE) {
            ColorStateList textColors = ((TextView) mView).getTextColors();

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_pressed},
                            new int[]{},
                    },
                    new int[]{
                            textColorDisabled,
                            textColorPressed,
                            textColor});

            ((TextView) mView).setTextColor(colorStateList);
        }
    }
}
