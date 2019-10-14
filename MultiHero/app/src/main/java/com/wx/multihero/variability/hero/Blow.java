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

public class Blow {
    public static final int NONE = 0;
    public static final int BLOCKING = 1;
    public static final int PUNCH = 2;
    public static final int FLYING_KICK = 3;
    public static final int LOW_KICK = 4;
    public static final int UPPERCUT = 5;
    public static final int THROWING_ITEM = 6;
    public static final int SPECIAL = 7;
    public static final int DOGDING = 8;
    public static final int DOWN_SPECIAL = 9;
    public static final int HIGH_KICK = 10;
    public static final int CLUB = 11;
    public static final int SHOOTING_POSITION = 12;
    public static final int ITEM_PICKUP = 13;
    public static final int SUPER_SPECIAL = 14;
    public static final int THROW = 15;
    public static final int WALK = 16;
    public static final int COUNT = 17;
    public int value;
    public float distance;
    public Blow(int value, float distance) {
        this.value = value;
        this.distance = distance;
    }
}
