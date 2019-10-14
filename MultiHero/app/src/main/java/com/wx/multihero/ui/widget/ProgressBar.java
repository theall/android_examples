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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Utils;

public class ProgressBar extends Widget implements Renderable {
    private float mProgress;
    private RectF mTempRect;

    public ProgressBar() {
        init();
    }

    public ProgressBar(RectF boundingRect) {
        super(boundingRect);
        init();
    }
    public ProgressBar(int id, RectF boundingRect) {
        super(id, boundingRect);
        init();
    }

    private void init() {
        mProgress = 0;
        mDrawingRect.inset(Utils.getRealWidth(2), Utils.getRealHeight(1));
        mTempRect = new RectF(mDrawingRect);
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }

    public void render(Canvas canvas, Paint paint) {
        Paint.Style oldStyle = paint.getStyle();
        int oldColor = paint.getColor();
        paint.setColor(Color.GREEN);
        canvas.drawRect(mBoundingRect, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        mTempRect.set(mDrawingRect);
        mTempRect.right = mDrawingRect.left + mDrawingRect.width() * mProgress;
        canvas.drawRect(mTempRect, paint);
        paint.setStyle(oldStyle);
        paint.setColor(oldColor);
    }

    public void positionChanged(float dx, float dy) {

    }
}
