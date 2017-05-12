package com.example.rotateworld.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.rotateworld.R;
import com.example.rotateworld.utils.RotateWorldController;


/**
 * Created by zhouying on 2017/3/23.
 */

public class HouseViewA extends HouseView {
    private RotateWorldController rotateWorldController;

    public HouseViewA(Context context) {
        this(context, null);
    }

    public HouseViewA(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseViewA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.bottom_sport_layout;
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

    public void setController(RotateWorldController rotateWorldController) {
        this.rotateWorldController = rotateWorldController;
    }
}
