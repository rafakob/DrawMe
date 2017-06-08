package com.rafakob.drawme.delegate;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.graphics.drawable.DrawableCompat;
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
    protected int tintColor;
    protected int tintMode;

    public DrawMeShapeText(Context context, View view) {
        super(context, view);
    }

    public DrawMeShapeText(Context context, View view, AttributeSet attrs) {
        super(context, view, attrs);
    }

    public DrawMeShapeText(Context context, View view, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, view, attrs, defStyleAttr);
    }

    protected void obtainTextAttributes(TypedArray a) {
        font = a.getString(R.styleable.DrawMeText_dm_font);
        textColor = a.getColor(R.styleable.DrawMeText_dm_textColor, getTitleColor(backColor));
        textColorPressed = a.getColor(R.styleable.DrawMeText_dm_textColorPressed, textColor);
        textColorDisabled = a.getColor(R.styleable.DrawMeText_dm_textColorDisabled, textColor);

        tintColor = a.getColor(R.styleable.DrawMeText_dm_drawableTint, 0);
        tintMode = a.getInt(R.styleable.DrawMeText_dm_drawableTintMode, 0);

        Drawable left = tintDrawable(a.getDrawable(R.styleable.DrawMeText_android_drawableLeft));
        Drawable start = tintDrawable(a.getDrawable(R.styleable.DrawMeText_android_drawableStart));
        Drawable top = tintDrawable(a.getDrawable(R.styleable.DrawMeText_android_drawableTop));
        Drawable right = tintDrawable(a.getDrawable(R.styleable.DrawMeText_android_drawableRight));
        Drawable end = tintDrawable(a.getDrawable(R.styleable.DrawMeText_android_drawableEnd));
        Drawable bottom = tintDrawable(a.getDrawable(R.styleable.DrawMeText_android_drawableBottom));

        ((TextView) mView).setCompoundDrawablesWithIntrinsicBounds(left == null ? start : left, top, right == null ? end : right, bottom);
    }

    @Override
    public void obtainAttributes(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super.obtainAttributes(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawMeText, defStyleAttr, 0);
        obtainTextAttributes(typedArray);
        typedArray.recycle();

        if (!TextUtils.isEmpty(font))
            applyFont();
    }

    private void applyFont() {
        ((TextView) mView).setTypeface(FontCache.get(mView.getContext(), font));
    }

    private Drawable tintDrawable(Drawable drawable) {
        return tintDrawable(drawable, tintColor, tintMode);
    }

    private Drawable tintDrawable(Drawable drawable, int tintColor, int tintMode) {
        if (drawable == null || tintColor == 0) return drawable;

        Drawable wrapDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrapDrawable, tintColor);

        PorterDuff.Mode mode = intToMode(tintMode);
        if (mode != null && mode != PorterDuff.Mode.CLEAR)
            DrawableCompat.setTintMode(wrapDrawable, mode);
        return wrapDrawable;
    }

    private PorterDuff.Mode intToMode(int val) {
        switch (val) {
            default:
            case 0:
                return PorterDuff.Mode.CLEAR;
            case 1:
                return PorterDuff.Mode.SRC;
            case 2:
                return PorterDuff.Mode.DST;
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 4:
                return PorterDuff.Mode.DST_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 6:
                return PorterDuff.Mode.DST_IN;
            case 7:
                return PorterDuff.Mode.SRC_OUT;
            case 8:
                return PorterDuff.Mode.DST_OUT;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 10:
                return PorterDuff.Mode.DST_ATOP;
            case 11:
                return PorterDuff.Mode.XOR;
            case 16:
                return PorterDuff.Mode.DARKEN;
            case 17:
                return PorterDuff.Mode.LIGHTEN;
            case 13:
                return PorterDuff.Mode.MULTIPLY;
            case 14:
                return PorterDuff.Mode.SCREEN;
            case 12:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    return PorterDuff.Mode.ADD;
                }
                break;
            case 15:
                return PorterDuff.Mode.OVERLAY;
        }
        return PorterDuff.Mode.CLEAR;
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

    @ColorInt
    private int getTitleColor(@ColorInt int color) {
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return (darkness < 0.35) ? getDarkerColor(color, 0.25f) : Color.WHITE;
    }

    @ColorInt
    private int getDarkerColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 1.0f) float transparency) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= transparency;
        return Color.HSVToColor(hsv);
    }
}
