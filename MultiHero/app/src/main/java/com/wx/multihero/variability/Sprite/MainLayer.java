package com.wx.multihero.variability.Sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.entity.Map;

import java.util.ArrayList;

public class MainLayer extends TilesLayer {
    private ArrayList<Player> mPlayerList = new ArrayList<Player>();

    public MainLayer() {
    }

    public void loadMap(Map map) {

    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        super.render(canvas, paint);
    }

    @Override
    public void step() {
        super.step();

        for(Player player : mPlayerList) {
            player.step();
        }
    }
}
