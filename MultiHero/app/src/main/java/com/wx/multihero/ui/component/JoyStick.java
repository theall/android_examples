package com.wx.multihero.ui.component;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.R;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.TouchableWidget;

public class JoyStick extends TouchableWidget {
    private Bitmap mBackgroundBitmap;// 视图背景图片  假设是一个圆盘
    private Bitmap mTouchMoveBitmap;// 视图中间的随手指移动的图片  假设是一个圆球
    private Bitmap mDirectionBmp;// 指示方向的图片  假设是一个箭头  整体是一个正方形的图片

    private float mRoundBgPadding;// 背景圆到view边界的像素
    private float mWholePadWidth;// 盘的宽度，包括箭头；并不是View的总宽度
    private float mWholePadHeight;// 盘的高度，包括箭头；并不是View的总宽度
    private float mRoundBgRadius;

    private float mTouchImageX;
    private float mTouchImageY;

    private float mTouchBmpDefaultX;// 滚动球图片默认左上角x
    private float mTouchBmpDefaultY;// 滚动球图片默认左上角y

    private boolean mIsMoving;
    private float mContentCenterX;// 控制盘中心点x坐标
    private float mContentCenterY;// 控制盘中心点y坐标
    private boolean mFixed;
    private boolean mShowDirection;// 是否显示方向指示图片
    private Matrix mRotateMatrix;
    private ValueAnimator mValueAnimatorResetX;
    private ValueAnimator mValueAnimatorResetY;

    public JoyStick(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);

        mFixed = true;
        mIsMoving = false;
        mShowDirection = true;
        mRoundBgPadding = Utils.getRealWidth(20);
        mWholePadWidth = Utils.getGoldenWidth();
        mWholePadHeight = mWholePadWidth;
        mRoundBgRadius = Utils.getRealWidth(90);
        mRotateMatrix = new Matrix();
        mValueAnimatorResetX = new ValueAnimator();
        mValueAnimatorResetX.setDuration(200);
        mValueAnimatorResetX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                mTouchImageX = (Float)animation.getAnimatedValue();
            }
        });

        mValueAnimatorResetY = new ValueAnimator();
        mValueAnimatorResetY.setDuration(200);
        mValueAnimatorResetY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                mTouchImageY = (Float)animation.getAnimatedValue();
            }
        });
    }

    public void loadAssets() {
        Bitmap tmpBgBmp = Utils.getBitmapFromResourceId(R.drawable.joystick_right_pad);
        Bitmap tmpTouchBmp = Utils.getBitmapFromResourceId(R.drawable.joystick_control_ball);
        Bitmap tmpDirectionBmp = Utils.getBitmapFromResourceId(R.drawable.joystick_arrow);
        mDirectionBmp = Bitmap.createScaledBitmap(tmpDirectionBmp, (int) mWholePadWidth, (int) mWholePadHeight, true);
        mBackgroundBitmap = Bitmap.createScaledBitmap(tmpBgBmp, (int) (mRoundBgRadius - mRoundBgPadding) * 2, (int) (mRoundBgRadius - mRoundBgPadding) * 2, true);
        mTouchMoveBitmap = Bitmap.createScaledBitmap(tmpTouchBmp, 180, 180, true);

        setupContentCenter();
        mTouchBmpDefaultX = mContentCenterX - mTouchMoveBitmap.getWidth() / 2;
        mTouchBmpDefaultY = mContentCenterY - mTouchMoveBitmap.getWidth() / 2;
        mTouchImageX = mTouchBmpDefaultX;
        mTouchImageY = mTouchBmpDefaultY;
    }

    public void render(Canvas canvas, Paint paint) {
        if (mBackgroundBitmap != null) {
            // 画背景圆
            canvas.drawBitmap(mBackgroundBitmap, mContentCenterX - mBackgroundBitmap.getWidth() / 2,
                    mContentCenterY - mBackgroundBitmap.getHeight() / 2, null);

            if (mShowDirection && mTouchBmpDefaultX != mTouchImageX && mTouchBmpDefaultY != mTouchImageY) {
                // 画方向指示箭头
                float rotationDegree = (float)calTwoPointAngleDegree(mContentCenterX, mContentCenterY,
                        mTouchImageX + mTouchMoveBitmap.getWidth() / 2, mTouchImageY + mTouchMoveBitmap.getWidth() / 2);
                drawRotateBitmap(canvas, mDirectionBmp, 180 - rotationDegree, mContentCenterX - mWholePadWidth / 2, mContentCenterY - mWholePadHeight / 2);
            }

            // 画中心控制圆圈
            canvas.drawBitmap(mTouchMoveBitmap, mTouchImageX, mTouchImageY, null);
        }
    }

    /**
     * @return 计算(x1, y1)和(x0, y0)两点之间的直线距离
     */
    private double calTwoPointDistant(double x0, double y0, double x1, double y1) {
        return Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow((y1 - y0), 2));
    }

    /**
     * @return 计算(x1, y1)相对于(x0, y0)为圆点的圆的角度[0, 360]
     */
    private double calTwoPointAngleDegree(double x0, double y0, double x1, double y1) {
        double z = calTwoPointDistant(x0, y0, x1, y1);
        double angle = Math.asin(Math.abs(y1 - y0) / z) * 180 / Math.PI;
        if (x1 < x0 && y1 < y0) {
            angle = 180 - angle;
        } else if (x1 < x0 && y1 >= y0) {
            angle = 180 + angle;
        } else if (x1 >= x0 && y1 >= y0) {
            angle = 360 - angle;
        }
        return angle;
    }

    /**
     * (x0, y0)为圆心
     * (x1, y1)为触摸点
     *
     * @param limitRadius 滚动球的圆心能移动的最大范围  限制滚动球（圆心）的移动半径
     * @return 计算得到滚动球的圆心坐标
     */
    private double[] calPointLocationByAngle(double x0, double y0, double x1, double y1, double limitRadius) {
        double angle = calTwoPointAngleDegree(x0, y0, x1, y1);

        float x = (float) (x0 + limitRadius * Math.cos(angle * Math.PI / 180));
        float y = (float) (y0 - limitRadius * Math.sin(angle * Math.PI / 180));

        return new double[]{x, y};
    }

    /**
     * @param canvas   画布
     * @param bitmap   要绘制的bitmap
     * @param rotation 旋转角度
     * @param posX     左上角顶点的x值 - left
     * @param posY     左上角顶点的y值 - top
     */
    private void drawRotateBitmap(Canvas canvas, Bitmap bitmap, float rotation, float posX, float posY) {
        int offsetX = bitmap.getWidth() / 2;
        int offsetY = bitmap.getHeight() / 2;
        mRotateMatrix.postTranslate(-offsetX, -offsetY);
        mRotateMatrix.postRotate(rotation);
        mRotateMatrix.postTranslate(posX + offsetX, posY + offsetY);
        canvas.drawBitmap(bitmap, mRotateMatrix, null);
    }

    // 设定初始位置
    private void setupContentCenter() {
        mContentCenterX = mWholePadWidth / 2;
        mContentCenterY = mWholePadHeight / 2;
    }
    
    public boolean processTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            mIsMoving = false;
            setupContentCenter();
            reset();
        } else if (action == MotionEvent.ACTION_DOWN) {
            if (event.getX() < mContentCenterX - mWholePadWidth / 2 || event.getX() > mContentCenterX + mWholePadWidth / 2) {
                return false; // 点击在圆盘外面的不处理
            } else if (event.getY() < mContentCenterY - mWholePadHeight / 2 || event.getY() > mContentCenterY + mWholePadHeight / 2) {
                return false;
            }
            mIsMoving = true;  // 直接移动圆球到点击位置
            userMoving(event);
        } else if (mIsMoving) {
            userMoving(event);
        }
        return true;
    }

    private void reset() {
        mValueAnimatorResetX.setFloatValues(mTouchImageX, mTouchBmpDefaultX);
        mValueAnimatorResetX.start();

        mValueAnimatorResetY.setFloatValues(mTouchImageY, mTouchBmpDefaultY);
        mValueAnimatorResetY.start();
    }

    private void userMoving(MotionEvent event) {
        if (mValueAnimatorResetX != null && mValueAnimatorResetY != null) {
            mValueAnimatorResetX.removeAllUpdateListeners();
            mValueAnimatorResetY.removeAllUpdateListeners();
        }

        float tr = (float)calTwoPointDistant(mContentCenterX, mContentCenterY, event.getX(), event.getY());
        double insideBgDis = (mBackgroundBitmap.getWidth() - mTouchMoveBitmap.getWidth()) / 2;
        if (tr <= insideBgDis) {
            // 点击在背景圆圈内
            onBallMove(event.getX(), event.getY());
        } else {
            // 点击后拖出了边界  计算出拖动圆的圆心坐标
            double dotCenterOnShow[] =calPointLocationByAngle(
                    mContentCenterX, mContentCenterY, event.getX(), event.getY(), insideBgDis);
            onBallMove((float) dotCenterOnShow[0], (float) dotCenterOnShow[1]);
        }
    }

    private void onBallMove(float ballCenterX, float ballCenterY) {
        mTouchImageX = ballCenterX - mTouchMoveBitmap.getWidth() / 2;
        mTouchImageY = ballCenterY - mTouchMoveBitmap.getHeight() / 2;
        float horizontalPercent = (ballCenterX - mContentCenterX) / (mBackgroundBitmap.getWidth() - mTouchMoveBitmap.getWidth()) * 2.0f;
        float verticalPercent = (mContentCenterY - ballCenterY) / (mBackgroundBitmap.getHeight() - mTouchMoveBitmap.getHeight()) * 2.0f;

    }

    public void positionChanged(float dx, float dy) {

    }
}
