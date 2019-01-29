package com.wx.multihero.ui.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Character;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.Sprite.Player;

public class CharacterPlatform extends Widget implements Player.CharacterChangedCallback {
    private RoundBoard mRoundBoard;
    private Stage mStage;
    private Player mBindValue;

    public CharacterPlatform(int id, RectF boundingRect) {
        super(id, boundingRect);
        mRoundBoard = new RoundBoard(id, null);
        mStage = new Stage(id, null);
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

    public int processTouchEvent(MotionEvent event) {
        mRoundBoard.processTouchEvent(event);
        mStage.processTouchEvent(event);
        return 0;
    }

    public void characterChanged(Character oldCharacter, Character newCharacter) {
        mStage.setBindValue(newCharacter);
    }
}
