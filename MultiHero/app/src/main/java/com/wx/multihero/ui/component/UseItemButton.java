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

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.Button;

public class UseItemButton extends Button {
    private Boolean mBindValue;
    private String mPrefix;
    private String mOn;
    private String mOff;
    public UseItemButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
        mPrefix = Utils.getStringFromResourceId(R.string.use_items) + "  ";;
        mOn = Utils.getStringFromResourceId(R.string.on);
        mOff = Utils.getStringFromResourceId(R.string.off);
    }

    public void loadAssets() {
        Bitmap buttonBackground = AssetsLoader.getInstance().loadBitmap("gfx/ui/but_ta.png");
        setBitmap(buttonBackground);
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
        update();
    }

    public void setBindValue(Boolean value) {
        mBindValue = value;
    }

    @Override
    public void touched() {
        if(mBindValue != null) {
            mBindValue = !mBindValue;
            update();
        }
    }

    private void update() {
        String text = mPrefix;
        if(mBindValue!=null && mBindValue) {
            text += mOn;
        } else {
            text += mOff;
        }
        setText(text);
    }
}
