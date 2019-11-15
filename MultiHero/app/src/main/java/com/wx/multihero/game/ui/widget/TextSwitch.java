package com.wx.multihero.game.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TextSwitch extends TouchableWidget {
    private BitmapText bitmapText;
    private boolean isOn;

    public TextSwitch(Callback callback, Widget parent) {
        super(callback, parent);

        bitmapText = new BitmapText(this);
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
        bitmapText.setText(isOn ? "ON" : "OFF");
        setBoundingRect(bitmapText.getBoundingRect());
    }

    public boolean isOn() {
        return isOn;
    }

    public void positionChanged(float dx, float dy) {
        bitmapText.offset(dx, dy);
    }

    public void render(Canvas canvas, Paint paint) {
        bitmapText.render(canvas, paint);
    }
}
