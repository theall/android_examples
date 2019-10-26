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

package com.wx.multihero.game.variability.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.Map;
import com.wx.multihero.game.entity.PawnPoint;
import com.wx.multihero.game.entity.Platform;
import com.wx.multihero.game.entity.Wall;
import com.wx.multihero.game.variability.Game;
import com.wx.multihero.game.variability.collide.Collide;
import com.wx.multihero.game.variability.hero.Hero;
import com.wx.multihero.game.variability.object.Plat;
import com.wx.multihero.game.variability.ui.Player;

import java.util.ArrayList;

public class MainLayer extends TilesLayer {
    private ArrayList<Hero> mHeroList = new ArrayList<Hero>();
    private ArrayList<Plat> mPlatList = new ArrayList<Plat>();
    private ChunkManager mChunkManager = ChunkManager.getInstance();
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
            hero.moveTo(pp.start.x, pp.start.y);
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
        for(Plat plat : mPlatList) {
            plat.render(canvas, paint);
        }
        mChunkManager.render(canvas, paint);
        if(Utils.DEBUG) {
            Paint.Style oldStyle = paint.getStyle();
            int oldColor = paint.getColor();
            paint.setStyle(Paint.Style.STROKE);
            for (Wall wall : mMap.getWallList()) {
                paint.setColor(Color.YELLOW);
                canvas.drawRect(wall.x, wall.y, wall.w+wall.x, wall.h+wall.y+1, paint);
                canvas.drawText("wall", wall.x, wall.y, paint);
            }
            paint.setStyle(oldStyle);
            paint.setColor(oldColor);
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
        mChunkManager.step();

        // Check if is death
        for(Hero hero : mHeroList) {
            if(hero.x<-2000 || hero.x>2000 || hero.y<-2000 || hero.y>2000) {
                hero.stop();
                hero.moveTo(100, 200);
            }
        }
        for(Hero hero : mHeroList) {
            boolean vCheck = false;
            Plat vPlat = null;
            // hero was on platform from last frame, check is still on it
            if(hero.mPlat==null || Collide.testFly(hero, (Plat)hero.mPlat)) {
                hero.setPlat(null);
                for (Plat plat : mPlatList) {
                    if (Collide.testLand(hero, plat)) {
                        hero.move(0, plat.y - hero.rect.bottom);
                        hero.setPlat(plat);
                        vPlat = plat;
                        vCheck = true;
                        break;
                    } else if(Collide.testMoveUp(hero, plat)) {
                        hero.move(0, plat.rect.bottom - hero.rect.top);
                        hero.sy = -hero.sy;
                        vPlat = plat;
                        vCheck = true;
                        break;
                    }
                }
            } else {
                vPlat = (Plat)hero.mPlat;
                vCheck = true;
            }

            boolean hCheck = false;
            for(Plat plat : mPlatList) {
                if(plat == vPlat)
                    continue;

                if(Collide.testMoveLeft(hero, plat)) {
                    hero.move(plat.rect.right-hero.rect.left, 0);
                    hCheck = true;
                } else if(Collide.testMoveRight(hero, plat)) {
                    hero.move(plat.x-hero.rect.right, 0);
                    hCheck = true;
                }
            }
            if(vCheck & hCheck)
                break;
        }
    }
}
