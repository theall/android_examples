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

import android.graphics.PointF;
import android.graphics.RectF;

import com.wx.multihero.base.Renderable;

import java.util.ArrayList;

public abstract class Widget implements Renderable {
    protected RectF mBoundingRect;
    protected RectF mDrawingRect;
    private Widget mParent = null;
    private int mTag;
    private ArrayList<Widget> mChildren = new ArrayList<Widget>();
    public interface Callback {
        void moved(float dx, float dy);
    }
    private Callback mCallback = null;

    public Widget(Widget parent) {
        mParent = parent;
        mBoundingRect = new RectF();
        mDrawingRect = new RectF();
        mTag = -1;
    }
    public int getTag() {
        return mTag;
    }

    public void setTag(int tag) {
        mTag = tag;
    }

    public Widget parent() {
        return mParent;
    }

    public ArrayList<Widget> children() {
        return mChildren;
    }

    public void addChild(Widget child) {
        if(child == null)
            return;
        if(mChildren.indexOf(child) != -1)
            return;
        mChildren.add(child);
    }
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public boolean touchTest(float x, float y) {
        return mBoundingRect.contains(x, y);
    }

    public RectF getBoundingRect() {
        return mBoundingRect;
    }

    public void setBoundingRect(RectF rect) {
        float dx = rect.left - mBoundingRect.left;
        float dy = rect.top - mBoundingRect.top;
        mBoundingRect.set(rect);
        positionChanged(dx, dy);
        if(mCallback != null) {
            mCallback.moved(dx, dy);
        }
    }

    public RectF getDrawingRect() {
        return mDrawingRect;
    }

    public void setDrawingRect(RectF rect) {
        mDrawingRect.set(rect);
    }

    public PointF getPos() {
        PointF pos = new PointF();
        pos.set(mBoundingRect.left, mBoundingRect.top);
        return pos;
    }

    public void moveTo(float x, float y) {
        if(mBoundingRect == null)
            return;

        float dx = x - mBoundingRect.left;
        float dy = y - mBoundingRect.top;
        mBoundingRect.offset(dx, dy);

        if(mDrawingRect != null) {
            mDrawingRect.offset(dx, dy);
        }
        positionChanged(dx, dy);
        if(mCallback != null) {
            mCallback.moved(dx, dy);
        }
    }

    public void offset(float dx, float dy) {
        if(mBoundingRect != null)
            mBoundingRect.offset(dx, dy);

        if(mDrawingRect != null)
            mDrawingRect.offset(dx, dy);

        positionChanged(dx, dy);
        if(mCallback != null) {
            mCallback.moved(dx, dy);
        }
    }

    public abstract void positionChanged(float dx, float dy);
}
