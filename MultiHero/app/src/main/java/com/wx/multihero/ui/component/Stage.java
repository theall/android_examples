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
package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.entity.Character;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.Widget;

public class Stage extends Widget {
    private PictureItem mStageBitmap;
    private PictureItem mForegroundBitmap;
    private Character mBindValue;

    public Stage(int id, RectF boundingRect) {
        super(id, boundingRect);

        mStageBitmap = new PictureItem(0, null, null);
        mForegroundBitmap = new PictureItem(0, null, null);
    }

    public void setBindValue(Character value) {
        mBindValue = value;
        if(mBindValue==null) {
            mForegroundBitmap.setBitmap(null);
        } else {
            mForegroundBitmap.setBitmap(value.getPreview());
        }
    }

    public void render(Canvas canvas, Paint paint) {
        mStageBitmap.render(canvas, paint);
        mForegroundBitmap.render(canvas, paint);
    }

    public void loadAssets() {
        Bitmap stageBitmap = AssetsLoader.getInstance().loadBitmap("gfx/ui/pad.png");
        mStageBitmap.setBitmap(stageBitmap);
        RectF rect = new RectF(mStageBitmap.getBoundingRect());
        rect.bottom *= 2;
        setBoundingRect(rect);
        mStageBitmap.moveTo(0, rect.bottom - stageBitmap.getHeight());
        rect.bottom -= stageBitmap.getHeight()/2;
        mForegroundBitmap.setBoundingRect(rect);
    }

    public void positionChanged(float dx, float dy) {
        mStageBitmap.offset(dx, dy);
        mForegroundBitmap.offset(dx, dy);
    }

    public boolean processTouchEvent(MotionEvent event) {
        return false;
    }
}
