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

package com.wx.multihero.variability.hero;

import com.wx.multihero.base.PointF;
import com.wx.multihero.variability.sprite.SerializedFrames;

public class Action extends SerializedFrames {
    public enum ID {
        NULL,
        READY,
        BLOCKING,
        JUMP,
        JUMP2,
        PUNCH,
        FLYING_KICK,
        LOW_KICK,
        UPPERCUT,
        THROWING_ITEM,
        SPECIAL,
        DOGDING,
        DOWN_SPECIAL,
        HIGH_KICK,
        CLUB,
        SHOOTING_POSITION,
        ITEM_PICKUP,
        SUPER_SPECIAL,
        THROW,
        WALK,
        WALK_IN_AIR,
        DUCK
    }
    private int mValue;
    private int mDistance;
    private boolean mBreakable;
    private PointF mVector = new PointF();
    private ID mId;

    public Action(ID id) {
        super();

        mValue = 0;
        mDistance = 0;
        mBreakable = false;
        mId = id;
    }

    public boolean isBreakable() {
        return mBreakable;
    }

    public void setBreakable(boolean breakable) {
        mBreakable = breakable;
    }

    public ID getId() {
        return mId;
    }

    public void setId(ID id) {
        mId = id;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }

    public int getDistance() {
        return mDistance;
    }

    @Override
    public void reset() {
        super.reset();
    }

    public PointF getVector() {
        return mVector;
    }

    public void setVector(float dx, float dy) {
        mVector.x = dx;
        mVector.y = dy;
    }
}
