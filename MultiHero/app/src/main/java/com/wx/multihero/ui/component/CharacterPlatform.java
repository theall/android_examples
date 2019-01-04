package com.wx.multihero.ui.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wx.multihero.ui.widget.TouchableWidget;

public class CharacterPlatform extends TouchableWidget {
    private RoundBoard mRoundBoard;
    private Platform mPlatform;
    public CharacterPlatform(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
        mRoundBoard = new RoundBoard(id, null);
        mPlatform = new Platform(id, null);
    }

    public void render(Canvas canvas, Paint paint) {
        if(mPlatform != null) {
            mPlatform.render(canvas, paint);
        }
        if(mRoundBoard != null) {
            mRoundBoard.render(canvas, paint);
        }
    }

    public void loadAssets() {
        mPlatform.loadAssets();
        mRoundBoard.loadAssets();
    }
}
