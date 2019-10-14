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
