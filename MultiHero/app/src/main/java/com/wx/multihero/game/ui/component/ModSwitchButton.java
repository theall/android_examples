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

package com.wx.multihero.game.ui.component;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.entity.Mod;
import com.wx.multihero.game.entity.ModManager;
import com.wx.multihero.game.ui.widget.SwitchMenu;
import com.wx.multihero.game.ui.widget.Widget;

public class ModSwitchButton extends SwitchMenu {
    private ModManager mBindValue;

    public ModSwitchButton(Callback callback, Widget parent) {
        super(callback, parent);
    }

    @Override
    public void selected(int id, Bundle parameters) {
        super.selected(id, parameters);

        if(mBindValue != null) {
            if(id == SwitchMenu.ID_BUTTON_LEFT) {
                mBindValue.shiftBack();
            } else {
                mBindValue.shiftNext();
            }
            update();
            touched();
        }
    }

    public void setBindValue(ModManager value) {
        mBindValue = value;
        update();
    }

    private void update() {
        if(mBindValue != null) {
            Mod mod = mBindValue.getCurrentMod();
            String name = mod!=null?mod.getName():"";
            setText(name);
        }
    }

    public void loadAssets() {
        Bitmap buttonBackground = AssetsLoader.getInstance().loadBitmap("gfx/ui/but_ta.png");
        Bitmap arrow1 = AssetsLoader.getInstance().loadBitmap("gfx/ui/arrow1.png");
        Bitmap arrow2 = AssetsLoader.getInstance().loadBitmap("gfx/ui/arrow2.png");
        setBitmaps(buttonBackground, arrow1, arrow2);
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
    }
}
