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

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Stepable;
import com.wx.multihero.entity.Layer;
import com.wx.multihero.entity.Tile;

import java.util.ArrayList;

public class TilesLayer implements Renderable,Stepable {
    private ArrayList<TileSprite> mTileSpriteList = new ArrayList<TileSprite>();

    public TilesLayer() {
    }

    public void loadFromLayer(Layer layer) {
        mTileSpriteList.clear();
        for(Tile tile : layer.getTileList()) {
            TileSprite tileSprite = new TileSprite();
            tileSprite.loadFromTile(tile);
            mTileSpriteList.add(tileSprite);
        }
    }

    public void render(Canvas canvas, Paint paint) {
        for(TileSprite tileSprite : mTileSpriteList) {
            tileSprite.render(canvas, paint);
        }
    }

    public void step() {
        for(TileSprite tileSprite : mTileSpriteList) {
            tileSprite.step();
        }
    }
}
