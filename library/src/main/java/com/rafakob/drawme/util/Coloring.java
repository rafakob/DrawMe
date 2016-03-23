package com.rafakob.drawme.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class Coloring {
    public static String intToHex(@ColorInt int color) {
        return String.format("#%06X", color);
    }

    @ColorInt
    public static int mix(int A, int B) {
        int rA = Color.red(A);
        int gA = Color.green(A);
        int bA = Color.blue(A);
        int aA = Color.alpha(A);

        int rB = Color.red(B);
        int gB = Color.green(B);
        int bB = Color.blue(B);
        int aB = Color.alpha(B);


        int rOut = (rA * aA / 255) + (rB * aB * (255 - aA) / (255 * 255));
        int gOut = (gA * aA / 255) + (gB * aB * (255 - aA) / (255 * 255));
        int bOut = (bA * aA / 255) + (bB * aB * (255 - aA) / (255 * 255));
        int aOut = aA + (aB * (255 - aA) / 255);

        return Color.argb(aOut, rOut, gOut, bOut);
    }

    @ColorInt
    public static int getThemeColor(@NonNull Context context, @AttrRes int attributeColor) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attributeColor, typedValue, true)) {
            return typedValue.data;
        }
        return 0;
    }

    public static float getColorBrightness(int normalColor) {
        int r = Color.red(normalColor);
        int g = Color.green(normalColor);
        int b = Color.blue(normalColor);
        return ((b + r + r + g + g + g) / 6 / 255f);
    }

    @ColorInt
    public static int getDrawableColor(ColorDrawable colorDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return colorDrawable.getColor();
        } else {
            // http://stackoverflow.com/questions/15982044/get-activity-background-color-in-android-api-level-8
            Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            colorDrawable.draw(canvas);
            final int color = bitmap.getPixel(0, 0);
            bitmap.recycle();
            return color;
        }
    }

    public static ColorStateList getColorStateList(int normalColor, int pressedColor, int disabledColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled,
                                -android.R.attr.state_pressed,
                                -android.R.attr.state_selected,
                                -android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        disabledColor,
                        pressedColor,
                        normalColor}
        );
    }
}
