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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.entity.Tile;

import java.util.ArrayList;

public class TileSprite extends AnimationSprite {
    public TileSprite() {
    }

    public void loadFromTile(Tile tile) {
        move(tile.x, tile.y);

        Bitmap bitmap = null;
        if(tile.nextTile == null) {
            add(getBitmapFromTile(tile));
        } else {
            ArrayList<Tile> existTileList = new ArrayList<Tile>();
            do {
                add(tile.duration, getBitmapFromTile(tile));
                existTileList.add(tile);
                tile = tile.nextTile;
            } while (tile.nextTile!=null && !existTileList.contains(tile));
        }
    }

    private Bitmap getBitmapFromTile(Tile tile) {
        AssetsLoader assetsLoader = AssetsLoader.getInstance();
        return assetsLoader.loadBitmap("gfx/tiles/%d_%d.png", tile.setNumber, tile.number);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        super.render(canvas, paint);
    }

    @Override
    public void step() {
        super.step();
    }
}
