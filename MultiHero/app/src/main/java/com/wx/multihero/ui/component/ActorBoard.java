package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.entity.Character;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.TouchableWidget;

public class ActorBoard extends TouchableWidget {
    private PictureItem mBackground;
    private Character mCharacter;
    private PictureItem mForeground;

    public ActorBoard(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);

        mBackground = new PictureItem(0, boundingRect, null);
        mForeground = new PictureItem(0, boundingRect, null);
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

    public void positionChanged(float dx, float dy) {
        if(mBackground.getBoundingRect() != null) {
            mBackground.offset(dx, dy);
        }
        if(mForeground.getBoundingRect() != null) {
            mForeground.offset(dx, dy);
        }
    }
}
