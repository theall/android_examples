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

package com.wx.multihero.game.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.wx.multihero.game.base.GameState;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.entity.Map;
import com.wx.multihero.game.variability.Game;
import com.wx.multihero.game.variability.ui.Debug;
import com.wx.multihero.game.variability.ui.Player;
import com.wx.multihero.os.TouchState;

import java.util.ArrayList;

public class GameScene extends BaseScene {
    private ControllerScene mControllerScene;
    private boolean mShowController;
    private Debug mDebug = new Debug(null);
    private Game mGame;
    public GameScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);

        mShowController = false;
        mControllerScene = new ControllerScene();
        mGame = Game.getInstance();
        mDebug.moveTo(400, 10);
    }

    public void render(Canvas canvas, Paint paint) {
        mGame.render(canvas, paint);
        mDebug.render(canvas, paint);

        if(mShowController)
            mControllerScene.render(canvas, paint);
    }

    public boolean processTouchState(TouchState touchState) {
        mControllerScene.processTouchState(touchState);
        mDebug.processTouchState(touchState);
        return false;
    }

    public void shiftIn() {
        Game.getInstance().setState(GameState.RUNNING);
    }

    public void shiftOut() {
        Game.getInstance().setState(GameState.EXITING);
    }

    public void loadAssets() {
        mControllerScene.loadAssets();
        mDebug.loadAssets();
    }

    public boolean ismShowController() {
        return mShowController;
    }

    public void setmShowController(boolean showController) {
        mShowController = showController;
    }

    public void setMap(Map map, ArrayList<Player> players) {
        mGame.loadPlayers(players);
        mGame.loadMap(map);
    }

    public void step() {
        mGame.step();
    }
}
