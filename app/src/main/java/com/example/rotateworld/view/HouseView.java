package com.example.rotateworld.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.rotateworld.R;
import com.example.rotateworld.listener.OnHouseShowListener;
import com.example.rotateworld.utils.RotateWorldController;

/**
 * Created by zhouying on 2017/3/28.
 */

public class HouseView extends RelativeLayout implements OnHouseShowListener {
    protected static final String TAG = "HouseView";
    public boolean isHouseShow;

    public HouseView(Context context) {
        super(context);
    }

    public HouseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HouseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.rotate_world_layout, this);
        initContent();
        initViews();
    }

    private void initContent() {
        LinearLayout contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        if (getContentViewId() != 0) {
            contentLayout.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , RotateWorldController.getHouseHeight());
            View mainView = LayoutInflater.from(getContext()).inflate(getContentViewId(), null);
            mainView.setLayoutParams(params);
            contentLayout.addView(mainView);
        }
        RelativeLayout.LayoutParams params = (LayoutParams) contentLayout.getLayoutParams();
        params.height = RotateWorldController.getHouseHeight();
        contentLayout.setPadding(0, contentLayout.getPaddingTop() + RotateWorld.SHADER_WIDTH, 0, 0);
        contentLayout.setLayoutParams(params);
    }

    protected void initViews() {

    }

    protected int getContentViewId() {
        return 0;
    }

    /**
     * 翻到这一页时会回调
     */
    @Override
    public void onHouseShow() {
        isHouseShow = true;
    }

    /**
     * 这一页隐藏时会回调
     */
    @Override
    public void onHouseHide() {
        isHouseShow = false;
    }

    /**
     * 所在activity或fragment销毁时会回调
     */
    @Override
    public void onDestroy() {

    }
}
