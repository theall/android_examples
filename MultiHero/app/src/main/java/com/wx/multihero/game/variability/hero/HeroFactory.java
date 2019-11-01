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

package com.wx.multihero.game.variability.hero;

import com.wx.multihero.game.entity.Character;

import java.util.ArrayList;
import java.util.HashMap;

import static com.wx.multihero.game.variability.hero.HeroFactory.ID.RYU;

public class HeroFactory {
    public enum ID {
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
    private static final HashMap<ID, Hero> ID_HERO_MAP = new HashMap<ID, Hero>();
    public static Hero create(ID id) {
        return ID_HERO_MAP.get(id);
    }

    public static void create(ArrayList<Character> characters) throws CloneNotSupportedException {
        for(Character character : characters) {
            create(character);
        }
    }

    /**
     * Retreive object of hero
     * @param index
     * @return hero object
     */
    public static Hero getHero(int index) {
        ID[] idList = ID.values();
        if(index<0 || index>=idList.length)
            return null;

        ID heroId = idList[index];
        return create(heroId);
    }

    private static Hero create(Character character) throws CloneNotSupportedException {
        int index = character.getId()-1;
        ID[] idList = ID.values();
        if(index<0 || index>=idList.length)
            return null;

        ID heroId = idList[index];
        Hero hero = ID_HERO_MAP.get(heroId);
        if(hero == null) {
            switch (heroId) {
                case RYU:
                    hero = new Ryu(character);
                    break;
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
            ID_HERO_MAP.put(heroId, hero);
        }
        if(hero != null)
            hero = (Hero)hero.clone();
        return hero;
    }
}
