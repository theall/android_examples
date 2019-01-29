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
import com.wx.multihero.variability.Sprite.Player;

public class RoundBoard extends Widget {
    private PictureItem mBackground;
    private PrimitiveText mPlayerName;
    private PlayerTypeButton mBtnPlayerType;
    private TeamBrick mBtnTeam;
    private static final int ID_TEAM = 1;
    private static final int ID_ACTOR_TYPE = 2;
    private Player mBindValue;
    
    public RoundBoard(int id, RectF boundingRect) {
        super(id, boundingRect);

        mBackground = new PictureItem(0, null, null);
        mPlayerName = new PrimitiveText(0, null);
        mPlayerName.setAlignment(Alignment.VERTICAL_CENTER);
        mBtnPlayerType = new PlayerTypeButton(ID_ACTOR_TYPE, null, null);
        mBtnTeam = new TeamBrick(ID_TEAM, null);
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

    public int processTouchEvent(MotionEvent event) {
        mBtnPlayerType.processTouchEvent(event);
        mBtnTeam.processTouchEvent(event);
        return 0;
    }
}
