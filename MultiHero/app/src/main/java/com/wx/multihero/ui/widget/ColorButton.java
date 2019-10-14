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
import android.graphics.Paint;
import android.graphics.RectF;

public class ColorButton extends TouchableWidget {
    private int mColor = 0xFF000000;

    public ColorButton(Callback callback, Widget parent) {
        super(callback, parent);
    }

    public void positionChanged(float dx, float dy) {

    }

    public void setColor(int color) {
        mColor = color;
    }

    public void render(Canvas canvas, Paint paint) {
        if(canvas != null) {
            int oldColor = paint.getColor();
            Paint.Style oldStyle = paint.getStyle();
            paint.setColor(mColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mBoundingRect, paint);
            paint.setColor(oldColor);
            paint.setStyle(oldStyle);
        }
    }
}
