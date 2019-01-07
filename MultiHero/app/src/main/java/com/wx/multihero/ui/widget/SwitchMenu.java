package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import com.wx.multihero.base.Utils;

public class SwitchMenu extends TouchableWidget implements TouchableWidget.Callback{
    private PrimitiveText mText;
    private Button mBtnLeft;
    private Button mBtnRight;
    private PictureItem mBackground;

    public enum ButtonType {
        LEFT,
        RIGHT
    }
    private static final int ID_TEXT = 1;
    private static final int ID_BUTTON_LEFT = 2;
    private static final int ID_BUTTON_RIGHT = 3;

    public SwitchMenu(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);

        mText = new PrimitiveText(ID_TEXT, null);
        mBtnLeft = new Button(ID_BUTTON_LEFT, null, this);
        mBtnRight = new Button(ID_BUTTON_RIGHT, null, this);
        mBackground = new PictureItem(0, null, null);
    }

    public Button getLeftButton() {
        return mBtnLeft;
    }

    public Button getRightButton() {
        return mBtnRight;
    }

    public void selected(int id, Bundle parameters) {

    }

    public void setText(String text) {
        if(mText != null) {
            mText.setText(text);
        }
    }
    public void setBitmaps(Bitmap background, Bitmap left, Bitmap right) {
        mBackground.setBitmap(background);
        mBtnLeft.setBitmaps(left);
        mBtnRight.setBitmaps(right);

        if(mBoundingRect.isEmpty()) {
            mBoundingRect.set(mBackground.getBoundingRect());
            float dx = Utils.getRealWidth(5);
            float dy = (mBoundingRect.height() - mBtnLeft.getBoundingRect().height()) / 2;
            mBtnLeft.moveTo(mBoundingRect.left + dx, mBoundingRect.top + dy);
            mBtnRight.moveTo(mBoundingRect.right - dx - mBtnRight.getBoundingRect().width(),
                    mBoundingRect.top + dy);

            RectF r = mText.getBoundingRect();
            r.left = mBtnLeft.getBoundingRect().right + dx;
            r.top = mBoundingRect.top;
            r.right = mBtnRight.getBoundingRect().left - dx;
            r.bottom = mBoundingRect.bottom;
            mText.setBoundingRect(r);
        }
    }

    @Override
    public void moveTo(float x, float y) {
        float dx = x - mBoundingRect.left;
        float dy = y - mBoundingRect.top;
        super.moveTo(x, y);
        mBackground.offset(dx, dy);
        mBtnLeft.offset(dx, dy);
        mBtnRight.offset(dx, dy);
        mText.offset(dx, dy);
    }

    public void render(Canvas canvas, Paint paint) {
        mBackground.render(canvas, paint);
        mText.render(canvas, paint);
        mBtnLeft.render(canvas, paint);
        mBtnRight.render(canvas, paint);
    }
}
