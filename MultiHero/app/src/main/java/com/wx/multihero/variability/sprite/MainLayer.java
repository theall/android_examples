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

package com.wx.multihero.variability.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.entity.Map;
import com.wx.multihero.entity.PawnPoint;
import com.wx.multihero.variability.hero.Hero;
import com.wx.multihero.variability.ui.Player;

import java.util.ArrayList;

public class MainLayer extends TilesLayer {
    private ArrayList<Hero> mHeroList = new ArrayList<Hero>();

    public MainLayer() {
    }

    public void loadMap(Map map, ArrayList<Player> playerList) {
        ArrayList<PawnPoint> pawnPoints = map.getPawnPointList();
        int pawnCount = pawnPoints.size();
        mHeroList.clear();
        for(Player player : playerList) {
            int index = player.getTag() % pawnCount;
            PawnPoint pp = pawnPoints.get(index);
            Hero hero = player.getHero();
            if(hero == null)
                continue;
            hero.move(pp.start.x, pp.start.y);
            mHeroList.add(hero);
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        for(Hero hero : mHeroList) {
            hero.render(canvas, paint);
        }
    }

    @Override
    public void step() {
        for(Hero hero : mHeroList) {
            hero.step();
        }
    }
}
