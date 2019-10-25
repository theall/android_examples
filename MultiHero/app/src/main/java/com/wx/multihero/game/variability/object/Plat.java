package com.wx.multihero.game.variability.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wx.multihero.game.entity.Platform;
import com.wx.multihero.game.variability.sprite.Sprite;

public class Plat extends Sprite {
    public Plat(Platform platform) {
        moveTo(platform.x, platform.y);
        rect.right = rect.left + platform.width;
        rect.bottom = rect.top + platform.height;
        ignoreGravity = true;
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        super.render(canvas, paint);

        Paint.Style oldStyle = paint.getStyle();
        int oldColor = paint.getColor();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom+1, paint);
        canvas.drawText("plat", x, y, paint);
        paint.setStyle(oldStyle);
        paint.setColor(oldColor);
    }
}
