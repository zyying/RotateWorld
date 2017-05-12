package com.example.rotateworld.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.rotateworld.R;


/**
 * Created by zhouying on 2017/3/23.
 */

public class HouseViewD extends HouseView {

    public HouseViewD(Context context) {
        this(context, null);
    }

    public HouseViewD(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseViewD(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected int getContentViewId() {
        return R.layout.bottom_compas_layout;
    }

    @Override
    protected void initViews() {
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
