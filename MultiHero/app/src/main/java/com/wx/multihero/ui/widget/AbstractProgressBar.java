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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Utils;

public abstract class AbstractProgressBar extends Widget {
    protected float mProgress;

    public enum Style {
        COLOR,
        TEXTURE
    }

    private Style mStyle;
    public AbstractProgressBar(Style style, Widget parent) {
        super(parent);

        mProgress = 1.0f;
        mStyle = style;
    }


    private void setStyle(Style style) {
        if(mStyle == style)
            return;

        mStyle = style;
    }

    public Style getStyle() {
        return mStyle;
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }

    public void positionChanged(float dx, float dy) {

    }
}
