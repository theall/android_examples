package com.wx.multihero.ui.component.joystick;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.R;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.TouchableWidget;

public class AxisHandler extends TouchableWidget {
    private float mTouchX;
    private float mTouchY;
    private float mTouchDefaultX;
    private float mTouchDefaultY;

    private boolean mIsMoving;
    private float mContentCenterX;
    private float mContentCenterY;
    private boolean mFixed;
    private boolean mShowDirection;
    private Matrix mRotateMatrix;
    private ValueAnimator mValueAnimatorResetX;
    private ValueAnimator mValueAnimatorResetY;
    private PictureItem mMovingBallPicture;
    private PictureItem mIndicatorPicture;
    private static int PAD_SIZE = 200;

    public AxisHandler(int id, RectF boundingRect) {
        super(id, boundingRect, null);

        mFixed = true;
        mIsMoving = false;
        mShowDirection = true;

        mRotateMatrix = new Matrix();
        mValueAnimatorResetX = new ValueAnimator();
        mValueAnimatorResetX.setDuration(200);
        mValueAnimatorResetX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                mTouchX = (Float)animation.getAnimatedValue();
            }
        });

        mValueAnimatorResetY = new ValueAnimator();
        mValueAnimatorResetY.setDuration(200);
        mValueAnimatorResetY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                mTouchY = (Float)animation.getAnimatedValue();
            }
        });

        mMovingBallPicture = new PictureItem(0, null, null);
        mIndicatorPicture = new PictureItem(0, null, null);
        PAD_SIZE = (int)(Utils.getRealWidth(PAD_SIZE));
    }

    public void loadAssets() {
        Bitmap tmpBgBmp = Utils.getBitmapFromResourceId(R.drawable.joystick_right_pad);
        Bitmap tmpTouchBmp = Utils.getBitmapFromResourceId(R.drawable.joystick_control_ball);
        Bitmap tmpDirectionBmp = Utils.getBitmapFromResourceId(R.drawable.joystick_arrow);
        tmpDirectionBmp = Bitmap.createScaledBitmap(tmpDirectionBmp, PAD_SIZE, PAD_SIZE, true);
        tmpBgBmp = Bitmap.createScaledBitmap(tmpBgBmp, PAD_SIZE, PAD_SIZE, true);
        tmpTouchBmp = Bitmap.createScaledBitmap(tmpTouchBmp, PAD_SIZE/2, PAD_SIZE/2, true);
        //setBitmap(tmpBgBmp);
        mMovingBallPicture.setBitmap(tmpTouchBmp);
        mIndicatorPicture.setBitmap(tmpDirectionBmp);

        setupContentCenter();

        mIndicatorPicture.moveTo(200, 200);
    }
    public void render(Canvas canvas, Paint paint) {
        if (mShowDirection && mTouchDefaultX != mTouchX && mTouchDefaultY != mTouchY) {
            float rotationDegree = (float)calTwoPointAngleDegree(mContentCenterX, mContentCenterY, mTouchX, mTouchY);
            Bitmap ball = mMovingBallPicture.getBitmap();
            drawRotateBitmap(canvas, ball, 180 - rotationDegree, mContentCenterX - ball.getWidth() / 2, mContentCenterY - ball.getHeight() / 2);
        }

        // indicator
        mIndicatorPicture.render(canvas, paint);

        // ball
        mMovingBallPicture.render(canvas, paint);
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
        mRotateMatrix.reset();
        mRotateMatrix.postTranslate(-offsetX, -offsetY);
        mRotateMatrix.postRotate(rotation);
        mRotateMatrix.postTranslate(posX + offsetX, posY + offsetY);
        canvas.drawBitmap(bitmap, mRotateMatrix, null);
    }

    // 设定初始位置
    private void setupContentCenter() {
        mContentCenterX = mBoundingRect.centerX();
        mContentCenterY = mBoundingRect.centerY();
        mTouchDefaultX = mContentCenterX;
        mTouchDefaultY = mContentCenterY;
        mTouchX = mTouchDefaultX;
        mTouchY = mTouchDefaultY;
    }

    public boolean processTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        if (action == MotionEvent.ACTION_UP) {
            mIsMoving = false;
            setupContentCenter();
            reset();
        } else if (action == MotionEvent.ACTION_DOWN) {
            if(!mBoundingRect.contains(x, y))
                return false;
            mIsMoving = true;  // 直接移动圆球到点击位置
            userMoving(x, y);
        } else if (mIsMoving) {
            userMoving(x, y);
        }
        return true;
    }

    private void reset() {
        mValueAnimatorResetX.setFloatValues(mTouchX, mTouchDefaultX);
        mValueAnimatorResetX.start();

        mValueAnimatorResetY.setFloatValues(mTouchY, mTouchDefaultY);
        mValueAnimatorResetY.start();
    }

    private void userMoving(float x, float y) {
        if (mValueAnimatorResetX != null && mValueAnimatorResetY != null) {
            mValueAnimatorResetX.removeAllUpdateListeners();
            mValueAnimatorResetY.removeAllUpdateListeners();
        }

        float tr = (float)calTwoPointDistant(mContentCenterX, mContentCenterY, x, y);
        double insideBgDis = PAD_SIZE / 2;
        if (tr <= insideBgDis) {
            // 点击在背景圆圈内
            onBallMove(x, y);
        } else {
            // 点击后拖出了边界  计算出拖动圆的圆心坐标
            double dotCenterOnShow[] =calPointLocationByAngle(
                    mContentCenterX, mContentCenterY, x, y, insideBgDis);
            onBallMove((float) dotCenterOnShow[0], (float) dotCenterOnShow[1]);
        }
    }

    private void onBallMove(float ballCenterX, float ballCenterY) {
        int ballWidth = mMovingBallPicture.getBitmap().getWidth();
        int ballHeight = mMovingBallPicture.getBitmap().getHeight();
        mTouchX = ballCenterX - ballWidth / 2;
        mTouchY = ballCenterY - ballHeight / 2;
        float horizontalPercent = (ballCenterX - mContentCenterX) / (mIndicatorPicture.getBitmap().getWidth() - ballWidth) * 2.0f;
        float verticalPercent = (mContentCenterY - ballCenterY) / (mIndicatorPicture.getBitmap().getHeight() - ballHeight) * 2.0f;
    }

    public void positionChanged(float dx, float dy) {
        mTouchDefaultX += dx;
        mTouchDefaultY += dy;
    }
}
