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

public class Alignment {
    public static final int HORIZONTAL_CENTER = 0x1;
    public static final int VERTICAL_CENTER = 0x2;
    public static final int CENTER = HORIZONTAL_CENTER|VERTICAL_CENTER;
    private int mValue;
    public Alignment() {
        mValue = CENTER;
    }
    public Alignment(int alignment) {
        mValue = alignment;
    }

    public boolean testFlag(int aligment) {
        return (mValue&aligment)==aligment;
    }

    public void set(int alignment) {
        mValue |= alignment;
    }

    public void unset(int alignment) {
        mValue ^= alignment;
    }

    public void setValue(int alignment) {
        mValue = alignment;
    }

    public boolean hasValue() {
        return mValue!=0;
    }
}
