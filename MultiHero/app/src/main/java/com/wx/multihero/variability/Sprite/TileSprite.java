package com.wx.multihero.variability.Sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.entity.Tile;

public class TileSprite extends Sprite {
    public TileSprite() {
    }

    public void loadFromTile(Tile tile) {
        x = tile.x;
        y = tile.y;
        AssetsLoader assetsLoader = AssetsLoader.getInstance();
        bitmap = assetsLoader.loadBitmap("gfx/tiles/%d_%d.png", tile.setNumber, tile.number);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        super.render(canvas, paint);
    }

    @Override
    public void step() {

    }
}
