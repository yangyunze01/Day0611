package com.example.zhoukao01;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 杨运泽 on 2018/6/11.
 */



public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量所有孩子的宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //最大的宽度，也就是FlowLayout父布局的宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int totalHeight = 0;
        View childView;
        int childWidth = 0;
        int childHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();

            if (childWidth > widthSize) {
                throw new IllegalArgumentException("子view宽度不能大于FlowLayout宽度");
            }

            childHeight = childView.getMeasuredHeight();
            if (lineWidth + childWidth > widthSize) {
                //换行width就是最大宽,只要又换行的情况出现，说明流式布局的宽度已经不够用了
                width = widthSize;
                //totalHeight不包含最后一行高度
                int preLineHeight = lineHeight;
                totalHeight += preLineHeight;
                lineHeight = childHeight;
                lineWidth = childWidth;

            } else {
                //不换行
                lineWidth += childWidth;
                //当前行的高度
                lineHeight = Math.max(lineHeight, childHeight);

                //假如只有一行，那测量的宽度就是当前行宽,如果又换行那就去最大宽
                width = Math.max(width, lineWidth);
            }

            //当结束遍历的时候要加上最后一行的高度
            if (i == getChildCount() - 1) {
                totalHeight += lineHeight;
                height = totalHeight;
            }

        }

        width = widthMode == MeasureSpec.EXACTLY ? widthSize : width;
        height = heightMode == MeasureSpec.EXACTLY ? heightSize : height;

        //确定最终测量的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineWidth = 0;
        int lineHeight = 0;
        int totalHeight = 0;
        View childView;
        int childWidth = 0;
        int childHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            if (lineWidth + childWidth > getMeasuredWidth()) {
                //换行
                //totalHeight不包含最后一行高度
                totalHeight += childHeight;
                lineWidth = 0;
                layoutChildView(childView, lineWidth, totalHeight, lineWidth + childWidth, totalHeight + childHeight);
                //换行width就是最大宽
                lineHeight = childHeight;
                lineWidth = childWidth;
            } else {
                //不换行
                layoutChildView(childView, lineWidth, totalHeight, lineWidth + childWidth, totalHeight + childHeight);
                lineWidth += childWidth;
                //当前行的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }

    }

    public void layoutChildView(View child, int l, int t, int r, int b) {
        child.layout(l, t, r, b);
    }
}
