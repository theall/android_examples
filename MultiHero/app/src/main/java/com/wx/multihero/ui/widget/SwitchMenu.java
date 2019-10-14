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

package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.base.Utils;

public class SwitchMenu extends TouchableWidget implements TouchableWidget.Callback{
    private PrimitiveText mText;
    protected Button mBtnLeft;
    protected Button mBtnRight;
    private PictureItem mBackground;

    private static final int ID_TEXT = 1;
    public static final int ID_BUTTON_LEFT = 2;
    public static final int ID_BUTTON_RIGHT = 3;

    public SwitchMenu(Callback callback, Widget parent) {
        super(callback, parent);

        mText = new PrimitiveText(this);
        mText.setTag(ID_TEXT);

        mBtnLeft = new Button(this, this);
        mBtnLeft.setTag(ID_BUTTON_LEFT);

        mBtnRight = new Button(this, this);
        mBtnRight.setTag(ID_BUTTON_RIGHT);

        mBackground = new PictureItem(this);
    }

    @Override
    public void setFireContinous(boolean fireContinous) {
        super.setFireContinous(fireContinous);
        mBtnLeft.setFireContinous(fireContinous);
        mBtnRight.setFireContinous(fireContinous);
    }

    public Button getLeftButton() {
        return mBtnLeft;
    }

    public Button getRightButton() {
        return mBtnRight;
    }

    public void setTouchedSoundEffect(int soundEffect) {
        mBtnLeft.setTouchedSoundEffect(soundEffect);
        mBtnRight.setTouchedSoundEffect(soundEffect);
    }

    @Override
    public boolean processTouchEvent(MotionEvent event) {
        boolean r = false;
        r |= mBtnLeft.processTouchEvent(event);
        r |= mBtnRight.processTouchEvent(event);
        return  r;
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
        mBtnLeft.setBitmap(left);
        mBtnRight.setBitmap(right);

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

    public void positionChanged(float dx, float dy) {
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
