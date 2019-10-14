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
import com.wx.multihero.variability.hero.Hero;
import com.wx.multihero.variability.ui.Player;

import java.util.ArrayList;

public class MainLayer extends TilesLayer {
    private ArrayList<Hero> mPlayerList = new ArrayList<Hero>();

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

        for(Hero hero : mPlayerList) {
            hero.step();
        }
    }
}
