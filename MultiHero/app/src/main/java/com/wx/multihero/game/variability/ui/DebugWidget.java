package com.wx.multihero.game.variability.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.ui.component.LifeSwitchButton;
import com.wx.multihero.game.ui.widget.TouchableWidget;
import com.wx.multihero.game.ui.widget.Widget;
import com.wx.multihero.game.variability.Game;
import com.wx.multihero.game.variability.hero.Hero;
import com.wx.multihero.os.TouchState;

public class DebugWidget extends Widget implements TouchableWidget.Callback, Stepable, Renderable {
    LifeSwitchButton mSwitch;
    public DebugWidget(Widget parent) {
        super(parent);
        mSwitch = new LifeSwitchButton(this, this);
    }

    public void positionChanged(float dx, float dy) {

    }

    public void render(Canvas canvas, Paint paint) {
        mSwitch.render(canvas, paint);

        Player player = Game.getInstance().getPlayer(0);
        Hero hero = player.getHero();
        canvas.drawText("pos " + hero.x + " " + hero.y, 20, 40, paint);
        canvas.drawText("speed " + hero.sx + " " + hero.sy, 20, 60, paint);
        canvas.drawText("action " + hero.getCurrentAction().getId().name(), 20, 80, paint);
    }

    public void selected(int id, Bundle parameters) {

    }

    public boolean processTouchState(TouchState touchState) {
        return mSwitch.processTouchState(touchState);
    }

    public void loadAssets() {
        mSwitch.loadAssets();
        mSwitch.moveTo(400, 10);
    }

    public void step() {
        Player player = Game.getInstance().getPlayer(0);
        Hero hero = player.getHero();
        if(hero != null) {
            mSwitch.setText(hero.getCurrentAction().getId().name());
        }
    }
}
