package com.example.rotateworld.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.rotateworld.R;


/**
 * Created by zhouying on 2017/4/6.
 */

public class HouseViewX extends HouseView {

    public HouseViewX(Context context) {
        this(context, null);
    }

    public HouseViewX(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseViewX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.bottom_speed_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    public void onHouseShow() {
        super.onHouseShow();
    }

    @Override
    public void onHouseHide() {
        super.onHouseHide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
