package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class TextureProgressBar extends AbstractProgressBar {
    private PictureItem mBackground;
    private PictureItem mForeground;

    public TextureProgressBar(Widget parent) {
        super(Style.TEXTURE, parent);
    }

    public Bitmap getForeground() {
        return mForeground.getBitmap();
    }

    private void setForeground(Bitmap foreground) {
        if(mForeground.getBitmap() == foreground)
            return;

        mForeground.setBitmap(foreground);
    }

    public Bitmap getBackground() {
        return mBackground.getBitmap();
    }

    private void setBackground(Bitmap background) {
        if(mBackground.getBitmap() == background)
            return;

        mBackground.setBitmap(background);
    }

    public void setBitmap(Bitmap foreground, Bitmap background) {
        mForeground.setBitmap(foreground);
        mBackground.setBitmap(background);

        RectF r = getBoundingRect();
        r.right = r.left + mBackground.getBoundingRect().width();
        r.bottom = r.top + mBackground.getBoundingRect().height();
        setBoundingRect(r);
    }

    public void render(Canvas canvas, Paint paint) {
        RectF r = getBoundingRect();
        canvas.translate(-r.left, -r.top);
        mBackground.render(canvas, paint);
        mForeground.render(canvas, paint);
        canvas.translate(r.left, r.top);
    }
}
