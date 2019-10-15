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
        UNKNNOWN,
        RYU,
        RASH,
        SPIDERMAN,
        MARIO,
        MIKEY,
        NINYA,
        BATMAN,
        ALIEN,
        GOKU,
        BELMONT,
        YUN,
        JIMMY
    }
    private static final HashMap<Integer, ID> ID_MAP = new HashMap<Integer, ID>();
    static {
        ID_MAP.put(0, RYU);
    }
    public static Hero create(Character character) {
        int id = character.getId();
        ID heroId = ID.values()[id];
        Hero hero = null;
        switch (heroId) {
            case RYU:
                hero = new Ryu(character);
            case RASH:
                hero = new Rash(character);
                break;
            case SPIDERMAN:

                break;
            case MARIO:

                break;
            case MIKEY:

                break;
            case NINYA:

                break;
            case BATMAN:

                break;
            case ALIEN:

                break;
            case GOKU:

                break;
            case BELMONT:

                break;
            case YUN:

                break;
            case JIMMY:

                break;
        }
        return new Ryu(character);
    }
}
