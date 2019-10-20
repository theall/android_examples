package com.wx.multihero.game.variability.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.game.ui.component.LifeSwitchButton;
import com.wx.multihero.game.ui.widget.TouchableWidget;
import com.wx.multihero.game.ui.widget.Widget;
import com.wx.multihero.os.TouchState;

public class Debug extends Widget implements TouchableWidget.Callback {
    LifeSwitchButton mSwitch;
    public Debug(Widget parent) {
        super(parent);
        mSwitch = new LifeSwitchButton(this, this);
    }

    public void positionChanged(float dx, float dy) {

    }

    public void render(Canvas canvas, Paint paint) {
        mSwitch.render(canvas, paint);
    }

    public void selected(int id, Bundle parameters) {

    }

    public boolean processTouchState(TouchState touchState) {
        return mSwitch.processTouchState(touchState);
    }

    public void loadAssets() {
        mSwitch.loadAssets();
    }
}
