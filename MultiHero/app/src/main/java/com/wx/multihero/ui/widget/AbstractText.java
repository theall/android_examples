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

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;

import com.wx.multihero.base.BigFont;

public abstract class AbstractText extends Widget {
    protected String mText = "";

    private Alignment mAlignment = new Alignment();
    public AbstractText(Widget parent) {
        super(parent);
    }

    public abstract float getStringWidth(String text);
    public abstract float getStringHeight(String text);

    public void setHCenter(Boolean center) {
        if(center) {
            mAlignment.set(Alignment.HORIZONTAL_CENTER);
        } else {
            mAlignment.unset(Alignment.HORIZONTAL_CENTER);
        }
        update();
    }

    public void setVCenter(Boolean center) {
        if(center) {
            mAlignment.set(Alignment.VERTICAL_CENTER);
        } else {
            mAlignment.unset(Alignment.VERTICAL_CENTER);
        }
        update();
    }

    public void setAlignment(int alignment) {
        mAlignment.setValue(alignment);
        update();
    }

    public void center() {
        mAlignment.setValue(Alignment.CENTER);
        update();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        update();
    }

    public void update() {
        if(mText.isEmpty())
            return;

        if(mBoundingRect.isEmpty()) {
            mBoundingRect.left = 0;
            mBoundingRect.top = 0;
            mBoundingRect.right = getStringWidth(mText);
            mBoundingRect.bottom = getStringHeight(mText);
        }

        if(mAlignment.testFlag(Alignment.HORIZONTAL_CENTER)) {
            mDrawingRect.left = mBoundingRect.left + (mBoundingRect.width()-getStringWidth(mText))/2;
        } else {
            mDrawingRect.left = mBoundingRect.left;
        }

        if(mAlignment.testFlag(Alignment.VERTICAL_CENTER)) {
            mDrawingRect.top = mBoundingRect.top + (mBoundingRect.height() - getStringHeight(mText)) / 2;
        } else {
            mDrawingRect.top = mBoundingRect.top;
        }
    }

    public void positionChanged(float dx, float dy) {
        update();
    }
}
