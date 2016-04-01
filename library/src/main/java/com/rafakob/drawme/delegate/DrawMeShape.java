package com.rafakob.drawme.delegate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import com.rafakob.drawme.R;
import com.rafakob.drawme.util.Coloring;

import java.util.Arrays;


/**
 * Base delegate that holds all shape attributes and accessors.
 */
public class DrawMeShape implements DrawMe {
    /* Widget */
    protected final View mView;
    /* Background color */
    protected int backColor;
    protected int backColorPressed;
    protected int backColorDisabled;
    /* Stroke */
    protected int stroke;
    protected int strokeColor;
    protected int strokeColorPressed;
    protected int strokeColorDisabled;
    /* Corner radius */
    protected int radius;
    protected int radiusBottomLeft;
    protected int radiusBottomRight;
    protected int radiusTopLeft;
    protected int radiusTopRight;
    /* Mask */
    protected float maskBrightnessThreshold;
    protected int maskColorPressed;
    protected int maskColorPressedInverse;
    protected int maskColorDisabled;
    /* Params */
    protected boolean rippleEffect;
    protected boolean rippleUseControlHighlight;
    protected boolean statePressed;
    protected boolean stateDisabled;
    protected boolean shapeEqualWidthHeight;
    protected boolean shapeRadiusHalfHeight;

    public DrawMeShape(Context context, View view, AttributeSet attrs) {
        this.mView = view;
        obtainAttributes(context, attrs);
    }

    public void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawMe);
        obtainShapeAttributes(typedArray);
        typedArray.recycle();
    }

    protected void obtainShapeAttributes(TypedArray a) {
        rippleEffect = a.getBoolean(R.styleable.DrawMe_dm_rippleEffect, true);
        rippleUseControlHighlight = a.getBoolean(R.styleable.DrawMe_dm_rippleUseControlHighlight, true);
        statePressed = a.getBoolean(R.styleable.DrawMe_dm_statePressed, true);
        stateDisabled = a.getBoolean(R.styleable.DrawMe_dm_stateDisabled, true);
        shapeEqualWidthHeight = a.getBoolean(R.styleable.DrawMe_dm_shapeEqualWidthHeight, false);
        shapeRadiusHalfHeight = a.getBoolean(R.styleable.DrawMe_dm_shapeRadiusHalfHeight, false);

        maskBrightnessThreshold = a.getFloat(R.styleable.DrawMe_dm_maskBrightnessThreshold, 0);
        maskColorPressed = a.getColor(R.styleable.DrawMe_dm_maskColorPressed, Color.parseColor("#1F000000"));
        maskColorPressedInverse = a.getColor(R.styleable.DrawMe_dm_maskColorPressedInverse, Color.parseColor("#1DFFFFFF"));
        maskColorDisabled = a.getColor(R.styleable.DrawMe_dm_maskColorDisabled, Color.parseColor("#6DFFFFFF"));

        stroke = a.getDimensionPixelSize(R.styleable.DrawMe_dm_stroke, 0);
        radius = a.getDimensionPixelSize(R.styleable.DrawMe_dm_radius, 0);
        radiusBottomLeft = a.getDimensionPixelSize(R.styleable.DrawMe_dm_radiusBottomLeft, -1);
        radiusBottomRight = a.getDimensionPixelSize(R.styleable.DrawMe_dm_radiusBottomRight, -1);
        radiusTopLeft = a.getDimensionPixelSize(R.styleable.DrawMe_dm_radiusTopLeft, -1);
        radiusTopRight = a.getDimensionPixelSize(R.styleable.DrawMe_dm_radiusTopRight, -1);

        backColor = a.getColor(R.styleable.DrawMe_dm_backColor, Color.TRANSPARENT);
        backColorPressed = a.getColor(R.styleable.DrawMe_dm_backColorPressed, defaultPressedColor(backColor));
        backColorDisabled = a.getColor(R.styleable.DrawMe_dm_backColorDisabled, defaultDisabledColor(backColor));

        strokeColor = a.getColor(R.styleable.DrawMe_dm_strokeColor, Color.GRAY);
        strokeColorPressed = a.getColor(R.styleable.DrawMe_dm_strokeColorPressed, defaultPressedColor(strokeColor));
        strokeColorDisabled = a.getColor(R.styleable.DrawMe_dm_strokeColorDisabled, defaultDisabledColor(strokeColor));
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (shapeRadiusHalfHeight) {
            radius = mView.getHeight() / 2;
        }
        updateLayout();
    }

    @Override
    public int[] onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] size = new int[2];
        if (shapeEqualWidthHeight && mView.getWidth() > 0 && mView.getHeight() > 0) {
            int max = Math.max(mView.getWidth(), mView.getHeight());
            int measureSpec = View.MeasureSpec.makeMeasureSpec(max, View.MeasureSpec.EXACTLY);
            size[0] = measureSpec;
            size[1] = measureSpec;
            return size;
        }
        size[0] = widthMeasureSpec;
        size[1] = heightMeasureSpec;
        return size;
    }

    @Override
    public void updateLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mView.setBackground(createBackground());
        } else {
            mView.setBackgroundDrawable(createBackground());
        }
    }

    private Drawable createBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && rippleEffect) {
            return createRippleDrawable();
        } else {
            return createStateListDrawable();
        }
    }

    /**
     * Creates background shape - setups background, stroke and radius.
     *
     * @param backgroundColor Background color.
     * @param strokeColor     Stroke color.
     * @return Shape drawable.
     */
    private Drawable createShape(int backgroundColor, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(backgroundColor);

        final float[] radiusArray = new float[8];
        Arrays.fill(radiusArray, radius);

        if (radiusTopLeft >= 0) {
            radiusArray[0] = radiusTopLeft;
            radiusArray[1] = radiusTopLeft;
        }

        if (radiusTopRight >= 0) {
            radiusArray[2] = radiusTopRight;
            radiusArray[3] = radiusTopRight;
        }

        if (radiusBottomRight >= 0) {
            radiusArray[4] = radiusBottomRight;
            radiusArray[5] = radiusBottomRight;
        }

        if (radiusBottomLeft >= 0) {
            radiusArray[6] = radiusBottomLeft;
            radiusArray[7] = radiusBottomLeft;
        }
        shape.setCornerRadii(radiusArray);
        shape.setStroke(stroke, strokeColor);
        return shape;
    }


    private StateListDrawable createStateListDrawable() {
        StateListDrawable states = new StateListDrawable();
        if (stateDisabled) {
            states.addState(new int[]{-android.R.attr.state_enabled}, createShape(backColorDisabled, strokeColorDisabled));
        }
        if (statePressed) {
            states.addState(new int[]{android.R.attr.state_pressed}, createShape(backColorPressed, strokeColorPressed));
        }
        states.addState(new int[]{}, createShape(backColor, strokeColor));
        return states;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable createRippleDrawable() {
        if (!statePressed) {
            return getRippleContentDrawable();
        } else if (backColorPressed == Color.TRANSPARENT && strokeColorPressed != Color.TRANSPARENT) {
            // highlight only stroke
            return new RippleDrawable(ColorStateList.valueOf(strokeColorPressed), getRippleContentDrawable(), createShape(Color.TRANSPARENT, Color.WHITE));

        } else {
            return new RippleDrawable(ColorStateList.valueOf(backColorPressed), getRippleContentDrawable(), createShape(Color.WHITE, Color.WHITE));
        }
    }

    /**
     * Creates content drawable for a RippleDrawable.
     *
     * @return
     */
    private Drawable getRippleContentDrawable() {
        if (!stateDisabled) {
            return createShape(backColor, strokeColor);
        } else {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{-android.R.attr.state_enabled}, createShape(backColorDisabled, strokeColorDisabled));
            states.addState(new int[]{}, createShape(backColor, strokeColor));
            return states;
        }
    }

    /**
     * Calculates default color value for pressed color.
     * On PreL mixes normal state color with a "shadow mask", on L uses theme attribute colorControlHighlight.
     *
     * @param normalColor
     * @return
     */
    @ColorInt
    private int defaultPressedColor(int normalColor) {
        if (maskBrightnessThreshold > 0 && Coloring.getColorBrightness(normalColor) < maskBrightnessThreshold) {
            return Coloring.mix(maskColorPressedInverse, normalColor);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && rippleUseControlHighlight) {
            return Coloring.getThemeColor(mView.getContext(), R.attr.colorControlHighlight);
        } else {
            return Coloring.mix(maskColorPressed, normalColor);
        }
    }

    /**
     * Calculates default color value for disabled color.
     * Mixes state color with a "lighter mask".
     *
     * @param normalColor
     * @return
     */
    @ColorInt
    private int defaultDisabledColor(int normalColor) {
        return Coloring.mix(maskColorDisabled, normalColor);
    }
}