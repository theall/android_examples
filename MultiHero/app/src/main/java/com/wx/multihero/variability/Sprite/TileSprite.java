package com.wx.multihero.variability.Sprite;

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
