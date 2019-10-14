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

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.Alignment;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.PrimitiveText;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.ui.Player;

public class RoundBoard extends Widget {
    private PictureItem mBackground;
    private PrimitiveText mPlayerName;
    private PlayerTypeButton mBtnPlayerType;
    private TeamBrick mBtnTeam;
    private static final int ID_TEAM = 1;
    private static final int ID_ACTOR_TYPE = 2;
    private Player mBindValue;
    
    public RoundBoard(Widget parent) {
        super(parent);

        mBackground = new PictureItem(this);
        mPlayerName = new PrimitiveText(this);
        mPlayerName.setAlignment(Alignment.VERTICAL_CENTER);
        mBtnPlayerType = new PlayerTypeButton(null, this);
        mBtnPlayerType.setTag(ID_ACTOR_TYPE);
        mBtnTeam = new TeamBrick(this);
        mBtnTeam.setTag(ID_TEAM);
    }

    public void setBindValue(Player value) {
        mBindValue = value;
        mBtnPlayerType.setBindValue(value);
        mBtnTeam.setBindValue(value);
        if(mBindValue != null) {
            mPlayerName.setText(mBindValue.getName());
        }
    }
    
    public void render(Canvas canvas, Paint paint) {
        mBackground.render(canvas, paint);
        mPlayerName.render(canvas, paint);
        mBtnPlayerType.render(canvas, paint);
        mBtnTeam.render(canvas, paint);
    }
    
    public void loadAssets() {
        mBackground.setBitmap(AssetsLoader.getInstance().loadBitmap("gfx/ui/board.png"));

        mBtnPlayerType.loadAssets();
        RectF backgroundRect = mBackground.getBoundingRect();
        RectF playerTypeRect = mBtnPlayerType.getBoundingRect();
        RectF r = new RectF();
        r.left = backgroundRect.right - playerTypeRect.width() - Utils.getRealWidth(16);
        r.top = backgroundRect.top + Utils.getRealHeight(10);
        mBtnPlayerType.moveTo(r.left, r.top);

        RectF rect = new RectF(playerTypeRect);
        rect.left = backgroundRect.left + Utils.getRealWidth(16);
        rect.right = playerTypeRect.right - Utils.getRealWidth(16);
        mPlayerName.setBoundingRect(rect);

        mBtnTeam.loadAssets();
        RectF btnTeamRect = mBtnTeam.getBoundingRect();
        mBtnTeam.moveTo(backgroundRect.left + (backgroundRect.width() - btnTeamRect.width()) / 2,
                backgroundRect.bottom - btnTeamRect.height() - Utils.getRealHeight(10));
        setBoundingRect(new RectF(mBackground.getBoundingRect()));
    }

    public void positionChanged(float dx, float dy) {
        mBackground.offset(dx, dy);
        mBtnTeam.offset(dx, dy);
        mPlayerName.offset(dx, dy);
        mBtnPlayerType.offset(dx, dy);
    }

    public boolean processTouchEvent(MotionEvent event) {
        mBtnPlayerType.processTouchEvent(event);
        mBtnTeam.processTouchEvent(event);
        return false;
    }
}
