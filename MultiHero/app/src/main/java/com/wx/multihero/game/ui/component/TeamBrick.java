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

package com.wx.multihero.game.ui.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.R;
import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.ui.widget.ColorButton;
import com.wx.multihero.game.ui.widget.PrimitiveText;
import com.wx.multihero.game.ui.widget.TouchableWidget;
import com.wx.multihero.game.ui.widget.Widget;
import com.wx.multihero.game.variability.ui.Player;
import com.wx.multihero.os.TouchState;

public class TeamBrick extends Widget implements Renderable,TouchableWidget.Callback,Player.TeamChangedCallback{
    private PrimitiveText mLabel;
    private PrimitiveText mTextNone;
    private ColorButton mBtnColor;
    private Player mBindValue;
    private boolean mRenderRect;

    public TeamBrick(Widget parent) {
        super(parent);

        mLabel = new PrimitiveText(this);
        mTextNone = new PrimitiveText(this);
        mBtnColor = new ColorButton(this, this);
        mRenderRect = false;
    }

    public void setBindValue(Player value) {
        if(mBindValue != null) {
            mBindValue.setTeamChangedCallback(null);
        }
        mBindValue = value;
        if(mBindValue != null) {
            mBindValue.setTeamChangedCallback(this);
        }

        update();
    }

    public void loadAssets() {
        RectF rect = new RectF();
        rect.left = 0;
        rect.top = 0;
        rect.right = Utils.getRealWidth(96);
        rect.bottom = Utils.getRealHeight(20);
        setBoundingRect(rect);

        rect.right /= 2;
        mLabel.setBoundingRect(rect);
        mLabel.setText(Utils.getStringFromResourceId(R.string.team));

        rect.offset(rect.right, 0);
        float paddingH = Utils.getRealWidth(2);
        float paddingV = Utils.getRealHeight(3);
        rect.left += paddingH;
        rect.right -= paddingH;
        rect.top += paddingV;
        rect.bottom -= paddingV;
        mBtnColor.setBoundingRect(rect);
        mBtnColor.setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("click"));

        mTextNone.setBoundingRect(rect);
        mTextNone.setText(Utils.getStringFromResourceId(R.string.none));
    }

    public void positionChanged(float dx, float dy) {
        mLabel.offset(dx, dy);
        mTextNone.offset(dx, dy);
        mBtnColor.offset(dx, dy);
    }

    public void render(Canvas canvas, Paint paint) {
        if(canvas == null)
            return;

        int oldColor = paint.getColor();
        paint.setColor(0xffC8C8C8);
        canvas.drawRect(mBoundingRect, paint);
        paint.setColor(oldColor);
        mLabel.render(canvas, paint);
        if(mRenderRect) {
            mBtnColor.render(canvas, paint);
        } else {
            mTextNone.render(canvas, paint);
        }
    }

    public void selected(int id, Bundle parameters) {
        if(mBindValue != null) {
            Player.Team team = mBindValue.getTeam();
            Player.Team newTeam = Player.Team.RED;
            switch (team) {
                case RED:
                    newTeam = Player.Team.GREEN;
                    break;
                case BLUE:
                    newTeam = Player.Team.RED;
                    break;
                case GREEN:
                    newTeam = Player.Team.BLUE;
                    break;
                case NONE:
                default:
                    break;
            }
            mBindValue.setTeam(newTeam);
            update();
        }
    }

    private void update() {
        if(mBindValue != null) {
            Player.Team team = mBindValue.getTeam();
            int color = Color.BLACK;
            switch (team) {
                case RED:
                    color = Color.RED;
                    break;
                case BLUE:
                    color = Color.BLUE;
                    break;
                case GREEN:
                    color = Color.GREEN;
                    break;
                case NONE:
                default:
                    break;
            }
            mBtnColor.setColor(color);
            mRenderRect = color!=Color.BLACK;
        } else {
            mRenderRect = false;
        }
    }

    public boolean processTouchState(TouchState touchState) {
        mBtnColor.processTouchState(touchState);
        return false;
    }

    public void teamChanged(Player.Team oldTeam, Player.Team newTeam) {
        update();
    }
}
