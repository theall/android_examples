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

package com.wx.multihero.game.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class MenuItem extends TouchableWidget {
    private BitmapText mText;

    public MenuItem(Widget parent) {
        super(parent);
        mText = new BitmapText(this);
    }

    public MenuItem(String text, Widget parent) {
        super(parent);
        mText = new BitmapText(this);
        mText.setText(text);
    }

    public MenuItem(String text, Callback callback, Widget parent) {
        super(callback, parent);
        mText = new BitmapText(this);
        mText.setText(text);
    }

    @Override
    public void setBoundingRect(RectF rect) {
        super.setBoundingRect(rect);

        mText.setBoundingRect(rect);
        mText.update();
    }

    public void render(Canvas canvas, Paint paint) {
        if(isTouchingDown()) {
            Paint.Style oldStyle = paint.getStyle();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GRAY);
            canvas.drawRect(mBoundingRect, paint);
            paint.setStyle(oldStyle);
        }
        mText.render(canvas, paint);
    }

    public void positionChanged(float dx, float dy) {
        mText.offset(dx, dy);
    }
}
