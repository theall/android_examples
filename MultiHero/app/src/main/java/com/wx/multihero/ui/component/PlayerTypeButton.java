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
import com.wx.multihero.ui.widget.Button;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.ui.Player;

public class PlayerTypeButton extends Button implements Player.TypeChangedCallback {
    private Bitmap mActorCpu;
    private Bitmap mActorHumen;
    private Bitmap mActorUnknown;
    private Player mBindValue;

    public PlayerTypeButton(Widget parent) {
        super(parent);
    }

    public PlayerTypeButton(Callback callback, Widget parent) {
        super(callback, parent);
    }

    @Override
    public void touched() {
        super.touched();
        if(mBindValue != null) {
            Player.Type newType = Player.Type.UNKNOWN;
            Player.Type playerType = mBindValue.getType();
            switch (playerType) {
                case CPU:
                    newType = Player.Type.HUM;
                    break;
                case HUM:
                    newType = Player.Type.UNKNOWN;
                    break;
                case UNKNOWN:
                    newType = Player.Type.CPU;
                    break;
                default:
                    break;
            }
            mBindValue.setType(newType);
            update();
        }
    }

    public void setBindValue(Player value) {
        if(mBindValue != null) {
            mBindValue.setTypeChangedCallback(null);
        }
        mBindValue = value;
        if(mBindValue != null) {
            mBindValue.setTypeChangedCallback(this);
        }
        update();
    }

    private void update() {
        Bitmap bitmap;
        if(mBindValue != null) {
            Player.Type playerType = mBindValue.getType();
            switch (playerType) {
                case CPU:
                    bitmap = mActorCpu;
                    break;
                case HUM:
                    bitmap = mActorHumen;
                    break;
                case UNKNOWN:
                default:
                    bitmap = mActorUnknown;
                    break;
            }
        } else {
            bitmap = mActorUnknown;
        }
        setBitmap(bitmap);
    }

    public void loadAssets() {
        mActorCpu = AssetsLoader.getInstance().loadBitmap("gfx/ui/butCPU.png");
        mActorHumen = AssetsLoader.getInstance().loadBitmap("gfx/ui/butHum.png");
        mActorUnknown = AssetsLoader.getInstance().loadBitmap("gfx/ui/butNA.png");
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
        update();
    }

    public void typeChanged(Player.Type oldType, Player.Type newType) {
        update();
    }
}
