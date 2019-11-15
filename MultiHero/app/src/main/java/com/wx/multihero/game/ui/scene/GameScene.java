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

package com.wx.multihero.game.ui.scene;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wx.multihero.game.base.GameState;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.entity.Map;
import com.wx.multihero.game.variability.Game;
import com.wx.multihero.game.variability.chunk.ChunkFactory;
import com.wx.multihero.game.variability.hero.HeroFactory;
import com.wx.multihero.game.variability.ui.DebugWidget;
import com.wx.multihero.game.variability.ui.Player;
import com.wx.multihero.os.TouchState;

import java.util.ArrayList;

public class GameScene extends BaseScene {     //游戏场景
    private ControllerScene mControllerScene;
    private boolean mShowController;
    private DebugWidget mDebugWidget = new DebugWidget(null);
    private Game mGame;
    public GameScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);

        mShowController = false;
        mControllerScene = new ControllerScene();
        mGame = Game.getInstance();
    }

    public void render(Canvas canvas, Paint paint) {
        mGame.render(canvas, paint);

        if(Utils.DEBUG) {
            mDebugWidget.render(canvas, paint);
        }

        if(mShowController) {
            mControllerScene.render(canvas, paint);
        }
    }

    public boolean processTouchState(TouchState touchState) {
        mControllerScene.processTouchState(touchState);
        mDebugWidget.processTouchState(touchState);
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

        if(Utils.DEBUG) {
            mDebugWidget.loadAssets();
        }
    }

    public boolean ismShowController() {
        return mShowController;
    }

    public void setmShowController(boolean showController) {
        mShowController = showController;
    }

    public void setMap(Map map, ArrayList<Player> players) throws Exception {
        mGame.loadMap(map, players);
    }

    public void step() {
        mGame.step();

        if(Utils.DEBUG) {
            mDebugWidget.step();
        }
    }
}
