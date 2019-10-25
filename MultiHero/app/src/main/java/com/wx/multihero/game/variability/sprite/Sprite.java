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

package com.wx.multihero.game.variability.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.wx.multihero.game.base.RectF;
import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.VectorF;
import com.wx.multihero.os.SoundPlayer;
import com.wx.multihero.game.base.Stepable;

public class Sprite implements Renderable, Stepable {
    public Bitmap bitmap;
    public float x;
    public float y;
    public float sx;
    public float sy;
    private FaceDir mFaceDir;
    public float accx;
    public float accy;
    public static float gravity;
    public int sound;
    private boolean mFlipHorizontal;
    public boolean ignoreGravity;
    public enum Anchor {
        LEFT_TOP,
        CENTER,
        CENTER_BOTTOM
    }
    private Anchor mAnchor;
    public boolean virtualized = false;
    private int bitmapHalfWidth;
    private int bitmapHeight;
    public RectF rect;
    public RectF lastRect;
    protected Sprite mPlat;
    protected Sprite mLastPlat;
    private boolean mLandEvent;
    public Sprite() {
        init();
    }

    public void init() {
        x = 0;
        y = 0;
        sx = 0;
        sy = 0;
        accx = 0;
        accy = 0;
        sound = -1;
        mAnchor = Anchor.LEFT_TOP;
        mFaceDir = FaceDir.NONE;
        ignoreGravity = false;
        rect = new RectF();
        lastRect = new RectF();
    }

    public void step() {
        lastRect.copyFrom(rect);

        sx += accx;
        if(mPlat==null && !ignoreGravity) {
            sy += gravity;
        }

        float realSx = mFaceDir==FaceDir.LEFT?-sx:sx;
        x += realSx;
        y += sy;

        rect.offset(realSx, sy);
    }

    public Anchor getAnchor() {
        return mAnchor;
    }

    public void setAnchor(Anchor anchor) {
        mAnchor = anchor;
    }

    public void moveTo(float x, float y) {
        lastRect.copyFrom(rect);
        this.x = x;
        this.y = y;
        rect.left = x - bitmapHalfWidth;
        rect.right = x + bitmapHalfWidth;
        rect.bottom = y;
        rect.top = y - bitmapHeight;
    }

    public void move(float dx, float dy) {
        lastRect.copyFrom(rect);
        this.x += dx;
        this.y += dy;
        rect.offset(dx, dy);
    }

    public void setBitmap(Bitmap bitmap) {
        if(this.bitmap == bitmap)
            return;

        this.bitmap = bitmap;
        if(bitmap != null) {
            bitmapHalfWidth = bitmap.getWidth()/2;
            bitmapHeight = bitmap.getHeight();
        } else {
            bitmapHalfWidth = 0;
            bitmapHeight = 0;
        }
        lastRect.copyFrom(rect);
        rect.left = x - bitmapHalfWidth;
        rect.right = x + bitmapHalfWidth;
        rect.top = y - bitmapHeight;
        rect.bottom = y;
    }

    public void render(Canvas canvas, Paint paint) {
        if(bitmap != null) {
            float x = this.x;
            float y = this.y;
            float width = bitmap.getWidth();
            float height = bitmap.getHeight();
            if(mAnchor == Anchor.CENTER) {
                x -= width/2;
                y -= height/2;
            } else if(mAnchor == Anchor.CENTER_BOTTOM) {
                x -= width/2;
                y -= height;
            }
            Matrix m = canvas.getMatrix();
            if(mFlipHorizontal) {
                m.setScale(-1, 1);
                m.postTranslate(x+width, y);
            } else {
                m.setTranslate(x, y);
            }
            canvas.drawBitmap(bitmap, m, paint);
        }
        if(sound != -1) {
            SoundPlayer.getInstance().playAudio(sound);
        }
    }

    public boolean isFlipHorizontal() {
        return mFlipHorizontal;
    }

    public void setFlipHorizontal(boolean flipHorizontal) {
        mFlipHorizontal = flipHorizontal;
    }

    public void stop() {
        sx = 0;
        sy = 0;
        accy = 0;
        accx = 0;
    }

    public void setSpeed(float x, float y) {
        sx = x;
        sy = y;
    }

    public void setSpeedX(float x) {
        sx = x;
    }

    public void setSpeedY(float y) {
        sy = y;
    }

    public FaceDir getFaceDir() {
        return mFaceDir;
    }

    public boolean setFaceDir(FaceDir faceDir) {
        if(mFaceDir == faceDir)
            return false;

        mFaceDir = faceDir;
        setFlipHorizontal(mFaceDir==FaceDir.LEFT);

//        float sx = Math.abs(this.sx);
//        setSpeedX(mFaceDir==FaceDir.LEFT?-sx:sx);
        return true;
    }

    public void addVector(float dx, float dy) {
        sx += x;
        sy += y;
    }

    public void addVector(VectorF vector) {
        sx += vector.x.value;
        sy += vector.y.value;
    }

    public void setVector(float x, float y) {
        sx = x;
        sy = y;
    }

    public void setVector(VectorF vector) {
        if(vector.isEmpty())
            return;

        if(vector.x.type == VectorF.Type.ABSOLUTE) {
            sx = vector.x.value;
        } else if(vector.x.type == VectorF.Type.RELATIVE) {
            sx += vector.x.value;
        }

        if(vector.y.type == VectorF.Type.ABSOLUTE) {
            sy = vector.y.value;
        } else if(vector.y.type == VectorF.Type.RELATIVE) {
            sy += vector.y.value;
        }
    }

    public void setPlat(Sprite plat) {
        mLastPlat = mPlat;
        mLandEvent = (mPlat==null) && plat!=null;
        mPlat = plat;
        if(plat != null)
            sy = 0.0f;
    }

    public boolean landEventOcour() {
        if(mLandEvent) {
            mLandEvent = false;
            return true;
        }
        return false;
    }

    public float getSpeedX() {
        return mFaceDir==FaceDir.LEFT?-sx:sx;
    }
}
