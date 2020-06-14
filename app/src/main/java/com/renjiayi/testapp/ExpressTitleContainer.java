package com.renjiayi.testapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ExpressTitleContainer extends ViewGroup {

    private final static int GAP_1 = 16;
    private final static int GAP_2 = 8;

    public ExpressTitleContainer(Context context) {
        super(context);
    }

    public ExpressTitleContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpressTitleContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        customMeasure();
    }

    private void customMeasure() {
        int count = getChildCount();
        int freeSizeSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int maxW = getMeasuredWidth() - getPaddingStart() - getPaddingEnd();
        if (count <= 0) {
            return;
        }

        View child;
        if (count == 1) {
            child = getChildAt(0);
            if (child.getVisibility() != GONE) {
                measureChild(child, MeasureSpec.makeMeasureSpec(maxW, MeasureSpec.AT_MOST), freeSizeSpec);
            }
        } else {
            // 这里是第1与第2，第2与第3 个视图之间的间距
            int totalWidth = GAP_1 + GAP_2 + getPaddingStart() + getPaddingEnd();
            for (int i = 0; i < count; i++) {
                child = getChildAt(i);
                if (child.getVisibility() != GONE) {
                    measureChild(child, freeSizeSpec, freeSizeSpec);
                    totalWidth += child.getMeasuredWidth();
                }
            }

            totalWidth -= getMeasuredWidth();
            child = getChildAt(0);
            if (totalWidth > 0 && child.getVisibility() != GONE) {
                measureChild(child, MeasureSpec.makeMeasureSpec(
                        child.getMeasuredWidth() - totalWidth, MeasureSpec.AT_MOST), freeSizeSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int start = getPaddingStart();
        int top;
        View child;
        for (int i = 0; i < count - 1; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                top = (getMeasuredHeight() - child.getMeasuredHeight()) / 2;
                child.layout(start, top, start + child.getMeasuredWidth(), top + child.getMeasuredHeight());
                start += GAP_1 + child.getMeasuredWidth();
            }
        }

        child = getChildAt(count - 1);
        top = (getMeasuredHeight() - child.getMeasuredHeight()) / 2;
        child.layout(r - getPaddingEnd() - child.getMeasuredWidth(), top,
                r - getPaddingEnd(), top + child.getMeasuredHeight());

    }

}

