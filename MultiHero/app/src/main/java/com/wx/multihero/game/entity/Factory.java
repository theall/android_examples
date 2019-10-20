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

package com.wx.multihero.game.entity;

import com.wx.multihero.game.base.LittleEndianDataInputStream;

import java.io.IOException;

public class Factory {
    public int x;
    public int y;
    public int dir;
    public int life;
    public int lives;
    public int team;
    public int damage;
    public int aiLevel;
    public int team_;
    public int category;
    public int type;
    public int delay;
    public int deadEvent;
    public int waitEvent;
    public int chunk;
    public int sound;
    public int var1;
    public int var2;
    public int var3;
    public int var4;
    public int var5;
    public Factory(LittleEndianDataInputStream inputStream) throws IOException {
        x = inputStream.readInt();
        y = inputStream.readInt();
        dir = inputStream.readInt();
        life = inputStream.readInt();
        lives = inputStream.readInt();
        team = inputStream.readInt();
        damage = inputStream.readInt();
        aiLevel = inputStream.readInt();
        team_ = inputStream.readInt();
        category = inputStream.readInt();
        type = inputStream.readInt();
        delay = inputStream.readInt();
        deadEvent = inputStream.readInt();
        waitEvent = inputStream.readInt();
        chunk = inputStream.readInt();
        sound = inputStream.readInt();
        var1 = inputStream.readInt();
        var2 = inputStream.readInt();
        var3 = inputStream.readInt();
        var4 = inputStream.readInt();
        var5 = inputStream.readInt();
    }
}
