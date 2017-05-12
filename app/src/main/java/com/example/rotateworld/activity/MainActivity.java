package com.example.rotateworld.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rotateworld.R;
import com.example.rotateworld.utils.RotateWorldController;
import com.example.rotateworld.view.RotateWorld;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RotateWorld rw;
    private RotateWorldController controller;
    private boolean isAnotherView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        rw = (RotateWorld) findViewById(R.id.rotate_world_layout);

        controller = new RotateWorldController(this);
        controller.setup(rw);
        findViewById(R.id.control_previous_view).setOnClickListener(this);
        findViewById(R.id.control_next_view).setOnClickListener(this);
        findViewById(R.id.control_next_next_view).setOnClickListener(this);
        findViewById(R.id.control_another_view).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_previous_view:
                controller.goPreviousPage();
                break;
            case R.id.control_next_view:
                controller.goNextPage();
                break;
            case R.id.control_next_next_view:
                controller.goNextNextPage();
                break;
            case R.id.control_another_view:
                if (!isAnotherView) {
                    controller.gotoAnotherView();
                }else{
                    controller.gotoMainView();
                }
                isAnotherView = !isAnotherView;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controller != null) {
            controller.destroyViews();
        }
    }
}
