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
import android.view.MotionEvent;

import com.wx.multihero.base.Renderable;

public class Button extends TouchableWidget implements Renderable {
    private Bitmap mNormal;
    private Bitmap mDown;
    private PrimitiveText mText;

    public Button(Widget parent) {
        super(parent);

        mText = new PrimitiveText(this);
    }

    public Button(Callback callback, Widget parent) {
        super(callback, parent);

        mText = new PrimitiveText(this);
    }

    public Button(String caption, Widget parent) {
        super(parent);

        mText = new PrimitiveText(this);
    }

    public String getText() {
        return mText.getText();
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public void setBitmap(Bitmap normal) {
        setBitmap(normal, normal);
    }

    public void setBitmap(Bitmap normal, Bitmap down) {
        mNormal = normal;
        mDown = down;
        if(mBoundingRect.isEmpty()) {
            mBoundingRect.left = 0;
            mBoundingRect.top = 0;
            mBoundingRect.right = mNormal.getWidth();
            mBoundingRect.bottom = mNormal.getHeight();

            if(mText != null)
                mText.setBoundingRect(mBoundingRect);
        }
    }

    public void render(Canvas canvas, Paint paint) {
        Bitmap bitmap = null;
        if(isTouchingDown()) {
            if(mDown != null) {
                bitmap = mDown;
            }
        } else {
            bitmap = mNormal;
        }
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, mBoundingRect.left, mBoundingRect.top, paint);
        }
        if(mText != null) {
            mText.render(canvas, paint);
        }
    }

    public boolean processTouchEvent(MotionEvent event) {
        super.processTouchEvent(event);
        return false;
    }

    public void positionChanged(float dx, float dy) {
        if(mText != null) {
            mText.offset(dx, dy);
        }
    }
}
