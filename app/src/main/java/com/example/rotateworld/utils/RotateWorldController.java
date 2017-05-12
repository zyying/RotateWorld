package com.example.rotateworld.utils;

import android.content.Context;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.rotateworld.MyApplication;
import com.example.rotateworld.view.HouseViewD;
import com.example.rotateworld.view.HouseView;
import com.example.rotateworld.view.HouseViewC;
import com.example.rotateworld.view.RotateWorld;
import com.example.rotateworld.view.HouseViewX;
import com.example.rotateworld.view.HouseViewA;
import com.example.rotateworld.view.HouseViewB;

import java.util.ArrayList;

import static com.example.rotateworld.view.RotateWorld.HEIGHT_SCALE;

/**
 * Created by zhouying on 2017/3/31.
 */

public class RotateWorldController {
    public static int houseHeight;
    public static int houseWidth;
    private Context context;
    private ArrayList<HouseView> views;
    private RotateWorld rw;
    private HouseViewX houseViewX;
    private HouseViewD houseViewD;
    private HouseViewC houseViewC;
    private HouseViewB houseViewB;
    private HouseViewA houseViewA;

    public RotateWorldController(Context context) {
        this.context = context;
    }


    public static int getHouseHeight() {
        if (houseHeight <= 0) {
            WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            houseWidth = wm.getDefaultDisplay().getWidth();
            houseHeight = (int) (houseWidth * HEIGHT_SCALE);
        }
        return houseHeight;
    }

    public void setup(RotateWorld rotateWorld) {
        this.rw = rotateWorld;
        RelativeLayout roateWorldParent = (RelativeLayout) rotateWorld.getParent();
        RelativeLayout.LayoutParams parentParams = (RelativeLayout.LayoutParams) (roateWorldParent.getLayoutParams());

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = (int) (width * HEIGHT_SCALE);
        parentParams.height = height;
        roateWorldParent.setLayoutParams(parentParams);

        houseViewC = new HouseViewC(context);
        houseViewB = new HouseViewB(context);
        houseViewD = new HouseViewD(context);
        houseViewA = new HouseViewA(context);
        houseViewA.setController(this);
        houseViewX = new HouseViewX(context);

        views = new ArrayList<>();
        gotoMainView();
    }

    public void destroyViews() {
        for (HouseView houseView : views) {
            houseView.onDestroy();
        }
        context = null;
    }

    public void goNextPage() {
        rw.goNextPage();
    }

    public void goPreviousPage() {
        rw.goPreviousPage();
    }

    public void goNextNextPage() {
        rw.setCurrentPage((rw.getCurrentPage() + 2) % 4);
    }

    public void gotoAnotherView() {
        if (views != null) {
            views.clear();
        }

        views.add(houseViewA);
        views.add(houseViewB);
        views.add(houseViewC);
        views.add(houseViewX);
        rw.setChildViews(views);

        houseViewD.onHouseHide();
    }

    public void gotoMainView() {
        if (views != null) {
            views.clear();
        }
        views.add(houseViewA);
        views.add(houseViewB);
        views.add(houseViewC);
        views.add(houseViewD);
        rw.setChildViews(views);

        houseViewX.onHouseHide();
    }
}
