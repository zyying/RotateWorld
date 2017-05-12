package com.example.rotateworld.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.rotateworld.R;


/**
 * Created by zhouying on 2017/3/23.
 */

public class HouseViewB extends HouseView{
    public HouseViewB(Context context) {
        this(context,null);
    }

    public HouseViewB(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseViewB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.bottom_statistics_layout,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
