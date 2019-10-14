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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.entity.Character;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.TouchableWidget;
import com.wx.multihero.ui.widget.Widget;

public class ActorBoard extends TouchableWidget {
    private PictureItem mBackground;
    private Character mCharacter;
    private PictureItem mForeground;

    public ActorBoard(Callback callback, Widget parent) {
        super(callback, parent);

        mBackground = new PictureItem(this);
        mForeground = new PictureItem(this);
    }

    public void setBindValue(Character value) {
        mCharacter = value;
        if(mCharacter != null) {
            Bitmap background = mCharacter.getPreview();
            if(background != null) {
                RectF r = new RectF();
                r.offsetTo(0, 0);
                r.right = background.getWidth();
                r.bottom = background.getHeight();
                mForeground.setBitmap(background);
                mForeground.center();
            }
        }
    }

    public Character getBindValue() {
        return mCharacter;
    }

    public void loadAssets() {
        Bitmap background = AssetsLoader.getInstance().loadBitmap("gfx/ui/board3.png");
        mBackground.setBitmap(background);
        if(mBoundingRect.isEmpty()) {
            RectF r = new RectF();
            r.offsetTo(0, 0);
            r.right = background.getWidth();
            r.bottom = background.getHeight();
            mBackground.setBoundingRect(r);
            mForeground.setBoundingRect(r);
            setBoundingRect(r);
        }
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/BHit.mp3"));
    }

    public void render(Canvas canvas, Paint paint) {
        mBackground.render(canvas, paint);
        mForeground.render(canvas, paint);
    }

    @Override
    public void setBoundingRect(RectF rect) {
        super.setBoundingRect(rect);

        mBackground.setBoundingRect(rect);
        mForeground.setBoundingRect(rect);
    }

    public void positionChanged(float dx, float dy) {
        if(mBackground.getBoundingRect() != null) {
            mBackground.offset(dx, dy);
        }
        if(mForeground.getBoundingRect() != null) {
            mForeground.offset(dx, dy);
        }
    }

}
