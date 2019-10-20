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

import com.wx.multihero.R;
import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.ui.widget.Widget;
import com.wx.multihero.game.variability.GameMode;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.ui.widget.Button;

public class GameModeBtton extends Button {
    private GameMode mBindValue;
    private String mPrefix;
    private String mDeathMatch;
    private String mHitTarget;
    private String mCatchFlag;
    public GameModeBtton(Widget parent) {
        super(parent);
        
        mPrefix = Utils.getStringFromResourceId(R.string.game_mode) + "  ";
        mDeathMatch = Utils.getStringFromResourceId(R.string.death_math);
        mHitTarget = Utils.getStringFromResourceId(R.string.hit_target);
        mCatchFlag = Utils.getStringFromResourceId(R.string.catch_flag);
    }
    
    public void setBindValue(GameMode value) {
        mBindValue = value;
    }

    public void loadAssets() {
        Bitmap buttonBackground = AssetsLoader.getInstance().loadBitmap("gfx/ui/but_ta.png");
        setBitmap(buttonBackground);
        setText(Utils.getStringFromResourceId(R.string.game_mode));
        setBitmap(buttonBackground);
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
        update();
    }

    @Override
    public void touched() {
        if(mBindValue != null) {
            GameMode.Type newType = GameMode.Type.DEATH_MATCH;
            switch (mBindValue.getType()) {
                case DEATH_MATCH:
                    newType = GameMode.Type.HIT_TARGET;
                    break;
                case HIT_TARGET:
                    newType = GameMode.Type.CATCH_FLAG;
                    break;
                case CATCH_FLAG:
                    newType = GameMode.Type.DEATH_MATCH;
                    break;
                default:
                    newType = GameMode.Type.HIT_TARGET;
                    break;
            }
            mBindValue.setType(newType);
            update();
        }
    }

    private void update() {
        if(mBindValue == null)
            return;

        String text = mPrefix;
        String modeString = mDeathMatch;
        switch (mBindValue.getType()) {
            case DEATH_MATCH:
                modeString = mDeathMatch;
                break;
            case HIT_TARGET:
                modeString = mHitTarget;
                break;
            case CATCH_FLAG:
                modeString = mCatchFlag;
                break;
            default:
                break;
        }
        text += modeString;
        setText(text);
    }
}
