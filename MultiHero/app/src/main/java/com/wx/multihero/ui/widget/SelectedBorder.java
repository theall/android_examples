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

public class SelectedBorder extends Widget implements Widget.Callback {
    protected Widget mHost;
    private boolean mRenderFlag;

    public SelectedBorder(Widget parent) {
        super(parent);

        mRenderFlag = true;
    }

    public Widget getHost() {
        return mHost;
    }

    public void setHost(Widget host) {
        if(mHost != null)
            mHost.setCallback(null);
        mHost = host;
        if(host != null) {
            mHost.setCallback(this);
            update();
        }
    }

    private void update() {
        if(mHost != null) {
            mBoundingRect.set(mHost.getBoundingRect());
            mBoundingRect.inset(-4, -3);
        }
    }

    public void moved(float dx, float dy) {
        update();
    }

    public void positionChanged(float dx, float dy) {

    }

    public void render(Canvas canvas, Paint paint) {
        if(mHost==null)
            return;

        mRenderFlag = !mRenderFlag;
        int oldColor = paint.getColor();
        Paint.Style oldStyle = paint.getStyle();
        int color = mRenderFlag?Color.RED:Color.WHITE;
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mBoundingRect, paint);
        paint.setColor(oldColor);
        paint.setStyle(oldStyle);
    }
}
