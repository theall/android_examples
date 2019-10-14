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
import android.graphics.Rect;
import android.graphics.RectF;

public class PrimitiveText extends AbstractText {
    private int mColor;
    private int mFontSize;

    public PrimitiveText(int id, RectF boundingRect) {
        super(id, boundingRect);

        mColor = Color.BLACK;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void setFontSize(int size) {
        mFontSize = size;
    }

    public float getStringWidth(String text) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(), rect);
        return rect.width();
    }

    public float getStringHeight(String text) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(), rect);
        return rect.height();
    }

    public void render(Canvas canvas, Paint paint) {
        if(canvas != null) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            float realTop = mDrawingRect.top + getStringHeight(mText) - fm.descent;
            int oldColor = paint.getColor();
            paint.setColor(mColor);
            canvas.drawText(mText, mDrawingRect.left, realTop, paint);
            paint.setColor(oldColor);
        }
    }
}
