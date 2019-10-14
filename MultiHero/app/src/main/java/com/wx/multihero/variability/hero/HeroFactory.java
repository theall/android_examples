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

import com.wx.multihero.entity.Character;

import java.util.HashMap;

import static com.wx.multihero.variability.hero.HeroFactory.ID.RYU;

public class HeroFactory {
    public enum ID {
        RYU,
        RASH
    }
    private static final HashMap<Integer, ID> ID_MAP = new HashMap<Integer, ID>();
    static {
        ID_MAP.put(0, RYU);
    }
    public static Hero create(Character character) {
        ID id = ID_MAP.get(character.getId());
        switch (id) {
            case RYU:
                return new Ryu(character);
        }
        return null;
    }
}
