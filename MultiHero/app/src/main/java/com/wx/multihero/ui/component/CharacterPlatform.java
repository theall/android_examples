package com.wx.multihero.ui.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.TouchableWidget;
import com.wx.multihero.variability.Player;

public class CharacterPlatform extends TouchableWidget {
    private RoundBoard mRoundBoard;
    private Stage mStage;
    private Player mBindValue;

    public CharacterPlatform(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
        mRoundBoard = new RoundBoard(id, null);
        mStage = new Stage(id, null);
    }

    public void setBindValue(Player bindValue) {
        mBindValue = bindValue;
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
}
