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

import android.graphics.PointF;
import android.graphics.RectF;
import com.wx.multihero.game.base.Renderable;
import java.util.ArrayList;

public abstract class Widget implements Renderable {
    protected RectF mBoundingRect;
    protected RectF mDrawingRect;
    private Widget mParent;
    private int mTag;
    private ArrayList<Widget> mChildren = new ArrayList<Widget>();
    public interface Callback {
        void moved(float dx, float dy);
        //void resized(float width, float height);
    }
    private Callback mCallback = null;

    public Widget(Widget parent) {
        mParent = parent;
        mBoundingRect = new RectF(0,0,0,0);
        mDrawingRect = new RectF(0,0,0,0);
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

    public void setBoundingRect(float left, float top, float width, float height) {
        mBoundingRect.left = left;
        mBoundingRect.top = top;
        mBoundingRect.right = mBoundingRect.left + width;
        mBoundingRect.bottom = mBoundingRect.top + height;
    }

    public void setBoundingRect(RectF rect) {
        float dx = rect.left - mBoundingRect.left;
        float dy = rect.top - mBoundingRect.top;
        mBoundingRect.set(rect);
        emitPositionChanged(dx, dy);
    }

    public void setSize(float width, float height) {
        mBoundingRect.right = mBoundingRect.left + width;
        mBoundingRect.bottom = mBoundingRect.top + height;
    }

    public RectF getDrawingRect() {
        return mDrawingRect;
    }

    public void setDrawingRect(RectF rect) {
        mDrawingRect.set(rect);
    }

    public PointF getPos() {
        return new PointF(mBoundingRect.left, mBoundingRect.top);
    }

    public void moveTo(float x, float y) {
        float dx = x - mBoundingRect.left;
        float dy = y - mBoundingRect.top;
        offset(dx, dy);
    }

    public void offset(float dx, float dy) {
        mBoundingRect.offset(dx, dy);
        mDrawingRect.offset(dx, dy);

        emitPositionChanged(dx, dy);
    }

    private void emitPositionChanged(float dx, float dy) {
        positionChanged(dx, dy);
        if(mCallback != null) {
            mCallback.moved(dx, dy);
        }
    }
    public abstract void positionChanged(float dx, float dy);
}
