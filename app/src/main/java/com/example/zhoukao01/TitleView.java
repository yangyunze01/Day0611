package com.example.zhoukao01;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 杨运泽 on 2018/6/11.
 */

public class TitleView extends LinearLayout {

    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0);
        String titleText = typedArray.getString(R.styleable.TitleView_title_text);

        //加载布局
        View inflate = inflate(context, R.layout.title_view_layout, this);
        TextView titleTv = inflate.findViewById(R.id.tv_title);
        
    }
}
