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

package com.wx.multihero.ui.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Character;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.ui.Player;

public class CharacterPlatform extends Widget implements Player.CharacterChangedCallback {
    private RoundBoard mRoundBoard;
    private Stage mStage;
    private Player mBindValue;

    public CharacterPlatform(Widget parent) {
        super(parent);
        mRoundBoard = new RoundBoard(this);
        mStage = new Stage(this);
    }

    public void setBindValue(Player bindValue) {
        if(mBindValue != null) {
            mBindValue.setCharacterCallback(null);
        }
        mBindValue = bindValue;
        if(mBindValue != null) {
            mBindValue.setCharacterCallback(this);
        }
        mRoundBoard.setBindValue(bindValue);
        mStage.setBindValue(bindValue.getCharacter());
    }

    public Player getBindValue() {
        return mBindValue;
    }

    public void render(Canvas canvas, Paint paint) {
        if(mStage != null) {
            mStage.render(canvas, paint);
        }
        if(mRoundBoard != null) {
            mRoundBoard.render(canvas, paint);
        }
    }

    public void loadAssets() {
        mRoundBoard.loadAssets();
        mStage.loadAssets();
        RectF roundBoardRect = new RectF(mRoundBoard.getBoundingRect());
        RectF stageRect = mStage.getBoundingRect();
        mStage.moveTo((roundBoardRect.width()-stageRect.width())/2, roundBoardRect.bottom + Utils.getRealHeight(20));
        roundBoardRect.union(stageRect);
        setBoundingRect(roundBoardRect);
    }

    public void positionChanged(float dx, float dy) {
        mRoundBoard.offset(dx, dy);
        mStage.offset(dx, dy);
    }

    public boolean processTouchEvent(MotionEvent event) {
        mRoundBoard.processTouchEvent(event);
        mStage.processTouchEvent(event);
        return false;
    }

    public void characterChanged(Character oldCharacter, Character newCharacter) {
        mStage.setBindValue(newCharacter);
    }
}
