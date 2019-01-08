package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Switch extends TouchableWidget {
    private boolean mIsOn = true;
    private PictureItem mBackgroundPicture;
    private PictureItem mSliderPicture;
    private static float MARGIN = 5.0f;

    public Switch(int id, RectF boundingRect, Callback callback, Bitmap background, Bitmap slider) {
        super(id, boundingRect, callback);

        mBackgroundPicture = new PictureItem(id, boundingRect, background);
        mSliderPicture = new PictureItem(id, boundingRect, slider);
        toggle(true);
    }

    public boolean isOn() {
        return mIsOn;
    }

    public void toggle(boolean isOn) {
        RectF newRect = new RectF(mBackgroundPicture.getDrawingRect());
        if(isOn) {
            newRect.offsetTo(newRect.right - mSliderPicture.getDrawingRect().width() - MARGIN,
                    mSliderPicture.getDrawingRect().top);
        } else {
            newRect.offsetTo(mSliderPicture.getDrawingRect().left + MARGIN,
                    mSliderPicture.getDrawingRect().top);
        }
        mSliderPicture.setDrawingRect(newRect);
        mIsOn = isOn;
    }

    @Override
    public void touched() {
        toggle(!mIsOn);
        super.touched();
    }

    public void render(Canvas canvas, Paint paint) {
        if(mBackgroundPicture != null) {
            mBackgroundPicture.render(canvas, paint);
        }
        if(mSliderPicture != null) {
            mSliderPicture.render(canvas, paint);
        }
    }

    public void positionChanged(float dx, float dy) {
        mBackgroundPicture.offset(dx, dy);
        mSliderPicture.offset(dx, dy);
    }
}
