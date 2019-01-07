package com.wx.multihero.ui.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.ui.widget.TouchableWidget;

public class CharacterPlatform extends TouchableWidget {
    private RoundBoard mRoundBoard;
    private Stage mStage;
    public CharacterPlatform(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
        mRoundBoard = new RoundBoard(id, null);
        mStage = new Stage(id, null);
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
        mStage.loadAssets();
        mRoundBoard.loadAssets();
    }
}
