package com.wx.multihero.variability.Sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.entity.Layer;
import com.wx.multihero.entity.Tile;

import java.util.ArrayList;

public class TilesLayer extends Sprite {
    private ArrayList<TileSprite> mTileSpriteList = new ArrayList<TileSprite>();

    public TilesLayer() {
    }

    public void loadFromLayer(Layer layer) {
        mTileSpriteList.clear();
        for(Tile tile : layer.getTileList()) {
            TileSprite tileSprite = new TileSprite();
            tileSprite.loadFromTile(tile);
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        for(TileSprite tileSprite : mTileSpriteList) {
            tileSprite.render(canvas, paint);
        }
    }

    @Override
    public void step() {
        for(TileSprite tileSprite : mTileSpriteList) {
            tileSprite.step();
        }
    }
}
