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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.SoundPlayer;

public class Sprite implements Renderable {
    private Bitmap bitmap;
    public float x;
    public float y;
    public float handX;
    public float handY;
    public int sound;

    public enum Anchor {
        LEFT_TOP,
        CENTER,
        CENTER_BOTTOM
    }
    private Anchor mAnchor = Anchor.LEFT_TOP;
    public Sprite() {
        x = 0;
        y = 0;
        handX = -1;
        handY = -1;
        sound = -1;
    }

    public Sprite(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void render(Canvas canvas, Paint paint) {
        if(bitmap != null) {
            float x = this.x;
            float y = this.y;
            if(mAnchor == Anchor.CENTER) {
                x -= bitmap.getWidth()/2;
                y -= bitmap.getHeight()/2;
            } else if(mAnchor == Anchor.CENTER_BOTTOM) {
                x -= bitmap.getWidth()/2;
                y -= bitmap.getHeight();
            }
            canvas.drawBitmap(bitmap, x, y, paint);
        }
        if(sound != -1) {
            SoundPlayer.playAudio(sound);
        }
    }
}
