package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.PrimitiveText;
import com.wx.multihero.ui.widget.Widget;

public class RoundBoard extends Widget {
    private PictureItem mBackground;
    private PictureItem mActorCpu;
    private PictureItem mActorHumen;
    private PictureItem mActorUnknown;
    private PrimitiveText mPlayerName;
    private TeamBrick mBtnTeam;
    private static final int ID_TEAM = 1;
    private static final int ID_ACTOR_TYPE = 2;

    public RoundBoard(int id, RectF boundingRect) {
        super(id, boundingRect);

        mBackground = new PictureItem(0, null, null);
        mActorCpu = new PictureItem(ID_ACTOR_TYPE, null, null);
        mActorHumen = new PictureItem(ID_ACTOR_TYPE, null, null);
        mActorUnknown = new PictureItem(ID_ACTOR_TYPE, null, null);
        mPlayerName = new PrimitiveText(0, null);
        mBtnTeam = new TeamBrick(ID_TEAM, null, null);
    }

    public void render(Canvas canvas, Paint paint) {
        mBackground.render(canvas, paint);
        mPlayerName.render(canvas, paint);
        mActorCpu.render(canvas, paint);
        mBtnTeam.render(canvas, paint);
    }

    private void configActorType(PictureItem actor, String resourcePath) {
        Bitmap bitmap = AssetsLoader.loadBitmap(resourcePath);
        actor.setBitmap(bitmap);
        RectF backgroundRect = mBackground.getBoundingRect();
        RectF actorRect = actor.getBoundingRect();
        RectF r = new RectF();
        r.left = backgroundRect.right - actorRect.width() - Utils.getRealWidth(16);
        r.right = r.left + actorRect.width();
        r.top = backgroundRect.top + Utils.getRealHeight(10);
        r.bottom = r.top + actorRect.height();
        actor.setBoundingRect(r);
    }
    public void loadAssets() {
        mBackground.setBitmap(AssetsLoader.loadBitmap("gfx/ui/board.png"));

        configActorType(mActorCpu, "gfx/ui/butCPU.png");
        configActorType(mActorHumen, "gfx/ui/butHum.png");
        configActorType(mActorUnknown, "gfx/ui/butNA.png");

        RectF rect = new RectF(mActorCpu.getBoundingRect());
        RectF backgroundRect = mBackground.getBoundingRect();
        rect.right = rect.left - Utils.getRealWidth(16);
        rect.left = backgroundRect.left + Utils.getRealWidth(16);
        mPlayerName.setBoundingRect(rect);
        mPlayerName.setText(Utils.getStringFromResourceId(R.string.player));

        mBtnTeam.loadAssets();
        RectF btnTeamRect = mBtnTeam.getBoundingRect();
        mBtnTeam.moveTo(backgroundRect.left + (backgroundRect.width() - btnTeamRect.width()) / 2,
                backgroundRect.bottom - btnTeamRect.height() - Utils.getRealHeight(10));
        setBoundingRect(new RectF(mBackground.getBoundingRect()));
    }

    public void positionChanged(float dx, float dy) {
        mBackground.offset(dx, dy);
        mActorCpu.offset(dx, dy);
        mActorHumen.offset(dx, dy);
        mActorUnknown.offset(dx, dy);
        mBtnTeam.offset(dx, dy);
        mPlayerName.offset(dx, dy);
    }
}
