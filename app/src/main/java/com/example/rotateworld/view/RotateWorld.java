package com.example.rotateworld.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import com.example.rotateworld.R;
import com.example.rotateworld.utils.MathUtils;

import java.util.ArrayList;

/**
 * Created by zhouying on 2017/3/22.
 */

public class RotateWorld extends ViewGroup {

    private static final String TAG = "RotateWorld";
    private final int ROTATE_DEGREE_MIN = 5;//手指滑动的最小距离
    private final float FADE_ANGLE = 10.0F;
    private final float WIDTH_SCALE = 1.827f;//八边形宽度相对于屏幕宽度的倍数
    public static final float HEIGHT_SCALE = 0.576f;//实现能看到的此view的高度相对于屏幕宽度的倍数
    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mLastX;
    private float mLastY;
    private Paint paint;
    private Paint shaderPaint;
    private Path path;
    private Path shaderPath;
    private int width, height;
    private int mulWidth;
    private int offsetWidth;//八边形最左边与屏幕左边的宽度差
    private int mDegree = 0;
    private int mLastDegree = 0;
    private int mulCenterX;//八边形中心点相对于此view的x坐标
    private int mulCenterY;//八边形中心点相对于此view的y坐标
    private int radius;//八边形中心与某个顶点的距离
    private ArrayList<HouseView> childViews = new ArrayList<HouseView>();
    private int rawCenterX;//八边形中心点相对于屏幕的x坐标
    private int rawCenterY;//八边形中心点相对于屏幕的y坐标

    private int currentPage = 0;
    public static final int SHADER_WIDTH = 30;//八边形周围阴影的宽度

    public RotateWorld(Context context) {
        this(context, null);
    }

    public RotateWorld(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateWorld(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new CornerPathEffect(50));

        shaderPaint = new Paint();
        shaderPaint.setShadowLayer(30, 0, 0, getResources().getColor(R.color.rotate_world_shader));//getResources().getColor(R.color.rotate_world_shader)
        shaderPaint.setAntiAlias(true);
        shaderPaint.setColor(getResources().getColor(R.color.rotate_world_shader));
        shaderPaint.setStyle(Paint.Style.FILL);
        shaderPaint.setPathEffect(new CornerPathEffect(50));

    }

    private void initPaths(int count, float radius) {
        if (path == null) {
            path = new Path();
            int sRadius = (int) (radius - SHADER_WIDTH);

            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    path.moveTo(sRadius * MathUtils.cos(360 / count * i), sRadius * MathUtils.sin(360 / count * i));//绘制起点
                } else {
                    path.lineTo(sRadius * MathUtils.cos(360 / count * i), sRadius * MathUtils.sin(360 / count * i));
                }
            }
            path.close();
        }

    }

    public void setChildViews(ArrayList<HouseView> views) {
        removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            if (i == 0) {
                views.get(i).setRotation(0);
            } else if (i == 1) {
                views.get(i).setRotation(90);
            } else if (i == 2) {
                views.get(i).setRotation(180);
            } else if (i == 3) {
                views.get(i).setRotation(-90);
            }
            addView(views.get(i));
            if (i == currentPage) {
                views.get(i).onHouseShow();
            } else {
                views.get(i).onHouseHide();
            }
        }
        this.childViews = views;
        if (currentPage != 1) {
            childViews.get(1).setAlpha(0);
        }
        invalidate();
    }

    public int getMulWidth() {
        return mulWidth;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void goNextPage() {
        setCurrentPage((currentPage + 4 + 1) % 4);
    }

    public void goPreviousPage() {
        setCurrentPage((currentPage + 4 - 1) % 4);
    }

    /**
     * @param page rage from 0 to 4
     */
    public void setCurrentPage(int page) {
        if (page < 0 || page > childViews.size() - 1) {
            return;
        }
        int deltaPage = currentPage - page;
        if (deltaPage == -1 || deltaPage == 3) {
            //左边1个
            smoothRotate(mLastDegree, mLastDegree - 90);
        } else if (deltaPage == 1 || deltaPage == -3) {
            //右边1个
            smoothRotate(mLastDegree, mLastDegree + 90);
        } else if (deltaPage == -2 || deltaPage == 2) {
            //右边2个
            smoothRotate(mLastDegree, mLastDegree - 180);
        } else {
            return;
        }
    }

    private void smoothRotate(int startDegree, int endDegree) {
        if (startDegree == endDegree) {
            return;
        }
        clearAnimation();
        Log.d(TAG, "startDegree " + startDegree + " endDegree " + endDegree);
        ValueAnimator animator = ValueAnimator.ofInt(startDegree, endDegree);
        int time = (int) (Math.abs(endDegree - startDegree) / 120f * 300);
        animator.setDuration(time);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                rotateTo((value + 360) % 360);
            }
        });
        animator.start();
        int lastPage = currentPage;
        currentPage = (4 - endDegree / 90) % 4;
        OnCurrentPageChanged(lastPage, currentPage);
        mLastDegree = (endDegree + 360) % 360;
    }

    private void OnCurrentPageChanged(int lastPage, int currentPage) {
        childViews.get(lastPage).onHouseHide();
        childViews.get(currentPage).onHouseShow();
    }

    /**
     * @param startDegree          range from 0 to 360
     * @param isClockWiseDirection 是否是顺时针旋转
     */
    private void stickyRotate(int startDegree, boolean isClockWiseDirection) {
        int endDegree;
        if (isClockWiseDirection) {
            endDegree = (startDegree + 90) / 90 * 90;
        } else {
            endDegree = (startDegree) / 90 * 90;
        }
        smoothRotate(startDegree, endDegree);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mulWidth = (int) (width * WIDTH_SCALE);
        offsetWidth = (mulWidth - width) / 2;
        radius = mulWidth / 2;
        mulCenterX = radius;
        mulCenterY = radius;
        rawCenterX = width / 2;
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = (int) (width * HEIGHT_SCALE);
        rawCenterY = wm.getDefaultDisplay().getHeight() - height + radius;

        setPivotX(mulCenterX);
        setPivotY(mulCenterY);
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.setMargins(-offsetWidth, 0, 0, 0);
        setLayoutParams(params);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        sizeWidth = (int) (sizeWidth * WIDTH_SCALE);
        int swidthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth, MeasureSpec.EXACTLY);
        int sheightMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth, MeasureSpec.EXACTLY);
        setMeasuredDimension(swidthMeasureSpec, sheightMeasureSpec);

        int count = childViews.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {

                final View child = childViews.get(i);

                final int swidth = Math.max(0, width);
                int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                        swidth, MeasureSpec.EXACTLY);

                final int sheight = Math.max(0, height);
                int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                        sheight, MeasureSpec.EXACTLY);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 遍历去设置menuitem的位置
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (i == 0) {
                child.layout(offsetWidth, 0, offsetWidth + width, height);
            } else if (i == 1) {
                child.layout(mulWidth - width, radius - width / 2, mulWidth, radius + width / 2);
            } else if (i == 2) {
                child.layout(offsetWidth, mulWidth - height, offsetWidth + width, mulWidth);
            } else if (i == 3) {
                child.layout(0, radius - width / 2, width, radius + width / 2);
            }
        }
        initPaths(8, radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMultiShape(canvas, 8, radius);
    }


    /**
     * @param canvas 画布
     * @param count  绘制几边形
     * @param radius //外圆的半径
     */
    public void drawMultiShape(Canvas canvas, int count, float radius) {
        canvas.save();
        canvas.translate(radius, radius);
        canvas.drawPath(path, shaderPaint);
        canvas.drawPath(path, paint);
        canvas.restore();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getRawX();//旋转时坐标会变，所以使用的是屏幕坐标
        float y = event.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitialMotionX = x;
                mInitialMotionY = y;
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float adx = x - mLastX;
                float ady = y - mLastY;
                if (ady * ady + adx * adx < 16) {
                    return true;
                }
                mLastX = x;
                mLastY = y;
                mDegree = computeDegree(mInitialMotionX, mInitialMotionY, mLastX, mLastY);
                rotateTo((mLastDegree + mDegree) % 360);
                break;
            case MotionEvent.ACTION_UP:
                boolean direction = isClockWiseDirection(mInitialMotionX, mInitialMotionY, mLastX, mLastY);
                if (mDegree == 0) {
                    break;
                }
                if (isDegreeTooLow()) {
                    direction = !direction;
                }
                stickyRotate((mLastDegree + mDegree + 360) % 360, direction);
                mDegree = 0;
                break;
        }
        return true;
    }

    private boolean isDegreeTooLow() {
        //较小的角度要反向
        if (mDegree > 180 && (360 - mDegree) <= ROTATE_DEGREE_MIN) {
            return true;
        } else if (Math.abs(mDegree) <= ROTATE_DEGREE_MIN) {
            return true;
        }
        return false;
    }

    private void rotateTo(int degree) {
        setRotation(degree);
        fadeInOrOut(degree);
    }

    private void fadeInOrOut(int degree) {
        if (degree >= 270) {
            if (degree <= 270 + FADE_ANGLE) {
                childViews.get(0).setAlpha((degree - 270) / FADE_ANGLE);
            } else {
                childViews.get(0).setAlpha(1);
            }
        } else if (degree <= 90) {
            if (degree >= 90 - FADE_ANGLE) {
                childViews.get(0).setAlpha((90 - degree) / FADE_ANGLE);
            } else {
                childViews.get(0).setAlpha(1);
            }
        } else {
            childViews.get(0).setAlpha(0);
        }
        if (degree <= 180 + FADE_ANGLE) {
            if (degree >= 180) {
                childViews.get(1).setAlpha((degree - 180) / FADE_ANGLE);
            } else {
                childViews.get(1).setAlpha(0);
            }
        } else if (degree >= 360 - FADE_ANGLE) {
            if (degree <= 360) {
                childViews.get(1).setAlpha((360 - degree) / FADE_ANGLE);
            } else {
                childViews.get(1).setAlpha(0);
            }
        } else {
            childViews.get(1).setAlpha(1);
        }

    }

    /**
     * 根据两个点计算角度，圆心是本view相对于屏幕的圆心坐标，
     *
     * @return 返回一个0到360之间的角度值
     */
    private int computeDegree(float startX, float startY, float endX, float endY) {

        double degree1 = Math.atan((endY - rawCenterY) / (endX - rawCenterX)) * 360 / Math.PI;
        double degree2 = Math.atan((startY - rawCenterY) / (startX - rawCenterX)) * 360 / Math.PI;
        degree1 = (degree1 + 360) % 360;
        degree2 = (degree2 + 360) % 360;
        int degree = (int) ((degree1 - degree2) / 3);
        return (degree + 360) % 360;
    }

    /**
     * 角度在0到180之间，cos值在1到-1
     */
    private boolean isClockWiseDirection(float startX, float startY, float endX, float endY) {
        double cosValue1 = ((startX - rawCenterX) / Math.sqrt(Math.pow(startY - rawCenterY, 2) + Math.pow(startX - rawCenterX, 2)));
        double cosValue2 = ((endX - rawCenterX) / Math.sqrt((Math.pow(endY - rawCenterY, 2) + Math.pow(endX - rawCenterX, 2))));
        return cosValue1 < cosValue2;
    }

}
