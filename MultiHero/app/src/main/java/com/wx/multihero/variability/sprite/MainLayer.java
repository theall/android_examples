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
import android.graphics.Color;
import android.graphics.Paint;

import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Map;
import com.wx.multihero.entity.PawnPoint;
import com.wx.multihero.entity.Platform;
import com.wx.multihero.entity.Wall;
import com.wx.multihero.variability.Game;
import com.wx.multihero.variability.collide.Collide;
import com.wx.multihero.variability.hero.Hero;
import com.wx.multihero.variability.object.Plat;
import com.wx.multihero.variability.ui.Player;

import java.util.ArrayList;

public class MainLayer extends TilesLayer {
    private ArrayList<Hero> mHeroList = new ArrayList<Hero>();
    private ArrayList<Plat> mPlatList = new ArrayList<Plat>();
    private Map mMap;
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

        mPlatList.clear();
        for(Platform platform : map.getPlatformList()) {
            Plat plat = new Plat(platform);
            mPlatList.add(plat);
        }

        mMap = map;
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        for(Hero hero : mHeroList) {
            hero.render(canvas, paint);
        }

        if(Utils.DEBUG) {
            paint.setStyle(Paint.Style.STROKE);
            for (Platform plat : mMap.getPlatformList()) {
                paint.setColor(Color.RED);
                canvas.drawRect(plat.x, plat.y, plat.width+plat.x, plat.height+plat.y+1, paint);
                canvas.drawText("plat", plat.x, plat.y, paint);
            }
            for (Wall wall : mMap.getWallList()) {
                paint.setColor(Color.YELLOW);
                canvas.drawRect(wall.x, wall.y, wall.w+wall.x, wall.h+wall.y+1, paint);
                canvas.drawText("wall", wall.x, wall.y, paint);
            }
        }
    }

    @Override
    public void step() {
        for(Hero hero : mHeroList) {
            hero.step();
        }
        for(Plat plat : mPlatList) {
            plat.step();
        }

        // Check if is death
        for(Hero hero : mHeroList) {
            if(hero.x<-2000 || hero.x>2000 || hero.y<-2000 || hero.y>2000) {
                hero.stop();
                hero.moveTo(100, 200);
            }
        }
        for(Hero hero : mHeroList) {
            Plat heroPlat = null;
            for(Plat plat : mPlatList) {
                boolean isCollided = Collide.test(hero, plat);
                if(isCollided) {
                    hero.y = plat.y;
                    heroPlat = plat;
                    break;
                }
            }

            // In the air
            if(heroPlat == null) {
                hero.gravity = Game.getInstance().getCurrentMap().gravity;
            } else if(hero.isInAir()){
                hero.gravity = 0;
            }
            hero.setPlat(heroPlat);
        }
    }
}
