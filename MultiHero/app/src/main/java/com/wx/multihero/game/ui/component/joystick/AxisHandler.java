/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.wx.multihero.game.ui.component.joystick;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.wx.multihero.R;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.ui.widget.PictureItem;
import com.wx.multihero.game.ui.widget.TouchableWidget;
import com.wx.multihero.game.ui.widget.Widget;
import com.wx.multihero.os.TouchState;

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

    public AxisHandler(Widget parent) {
        super(parent);

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

        mMovingBallPicture = new PictureItem(this);
        mIndicatorPicture = new PictureItem(this);
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
     * @return Compute the distance between (x1, y1) and (x0, y0)
     */
    private double calTwoPointDistant(double x0, double y0, double x1, double y1) {
        return Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow((y1 - y0), 2));
    }

    /**
     * @return Compute the angle of (x1, y1) to (x0, y0)
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
     * (x0, y0) the circle center
     * (x1, y1) touch point
     *
     * @param limitRadius maximize radius ball can moveTo
     * @return the center of moving ball
     */
    private double[] calPointLocationByAngle(double x0, double y0, double x1, double y1, double limitRadius) {
        double angle = calTwoPointAngleDegree(x0, y0, x1, y1);

        float x = (float) (x0 + limitRadius * Math.cos(angle * Math.PI / 180));
        float y = (float) (y0 - limitRadius * Math.sin(angle * Math.PI / 180));

        return new double[]{x, y};
    }

    /**
     * @param canvas
     * @param bitmap
     * @param rotation
     * @param posX     left
     * @param posY     top
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

    private void setupContentCenter() {
        mContentCenterX = mBoundingRect.centerX();
        mContentCenterY = mBoundingRect.centerY();
        mTouchDefaultX = mContentCenterX;
        mTouchDefaultY = mContentCenterY;
        mTouchX = mTouchDefaultX;
        mTouchY = mTouchDefaultY;
    }

    public boolean processTouchState(TouchState touchState) {
        TouchState.Action action = touchState.getAction();
        float x = touchState.getX();
        float y = touchState.getY();
        if (touchState.isReleased()) {
            mIsMoving = false;
            setupContentCenter();
            reset();
        } else if (touchState.isPressed()) {
            if(!mBoundingRect.contains(x, y))
                return false;
            mIsMoving = true;
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
            onBallMove(x, y);
        } else {
            double dotCenterOnShow[] = calPointLocationByAngle(
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
