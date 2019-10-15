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

package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.Button;
import com.wx.multihero.ui.widget.Widget;

public class BackwardButton extends Button {
    public BackwardButton(Callback callback, Widget parent) {
        super(callback, parent);
    }

    public void loadAssets() {
        Bitmap backBitmap = AssetsLoader.getInstance().loadBitmap("gfx/ui/backward.png");
        setBitmap(backBitmap, backBitmap);
        moveTo(Utils.getRealWidth(10), Utils.getScreenHeight() - backBitmap.getHeight() - Utils.getRealHeight(40));
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
    }
}
