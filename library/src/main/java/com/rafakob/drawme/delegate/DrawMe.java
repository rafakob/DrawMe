package com.rafakob.drawme.delegate;

public interface DrawMe {
    void updateLayout();

    void onLayout(boolean changed, int left, int top, int right, int bottom);

    int[] onMeasure(int widthMeasureSpec, int heightMeasureSpec);
}
