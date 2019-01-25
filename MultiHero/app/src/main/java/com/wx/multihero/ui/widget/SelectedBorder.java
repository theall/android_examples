package com.wx.multihero.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class SelectedBorder extends Widget implements Widget.Callback {
    protected Widget mHost;
    private boolean mRenderFlag;
    public SelectedBorder(int id, RectF boundingRect) {
        super(id, boundingRect);

        mRenderFlag = true;
    }

    public Widget getHost() {
        return mHost;
    }

    public void setHost(Widget host) {
        if(mHost != null)
            mHost.setCallback(null);
        mHost = host;
        if(host != null) {
            mHost.setCallback(this);
            update();
        }
    }

    private void update() {
        if(mHost != null) {
            mBoundingRect.set(mHost.getBoundingRect());
            mBoundingRect.inset(-4, -3);
        }
    }

    public void moved(float dx, float dy) {
        update();
    }

    public void positionChanged(float dx, float dy) {

    }

    public void render(Canvas canvas, Paint paint) {
        mRenderFlag = !mRenderFlag;
        if(mHost==null)
            return;

        int oldColor = paint.getColor();
        Paint.Style oldStyle = paint.getStyle();
        int color = mRenderFlag?Color.RED:Color.WHITE;
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mBoundingRect, paint);
        paint.setColor(oldColor);
        paint.setStyle(oldStyle);
    }
}
