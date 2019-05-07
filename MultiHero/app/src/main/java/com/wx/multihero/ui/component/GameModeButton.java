package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.variability.GameMode;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.Button;

public class GameModeButton extends Button {
    private GameMode mBindValue;
    private String mPrefix;
    private String mDeathMatch;
    private String mHitTarget;
    private String mCatchFlag;
    public GameModeButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
        
        mPrefix = Utils.getStringFromResourceId(R.string.game_mode) + "  ";
        mDeathMatch = Utils.getStringFromResourceId(R.string.death_math);
        mHitTarget = Utils.getStringFromResourceId(R.string.hit_target);
        mCatchFlag = Utils.getStringFromResourceId(R.string.catch_flag);
    }
    
    public void setBindValue(GameMode value) {
        mBindValue = value;
    }

    public void loadAssets() {
        Bitmap buttonBackground = AssetsLoader.getInstance().loadBitmap("gfx/ui/but_ta.png");
        setBitmap(buttonBackground);
        setText(Utils.getStringFromResourceId(R.string.game_mode));
        setBitmap(buttonBackground);
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
        update();
    }

    @Override
    public void touched() {
        if(mBindValue != null) {
            GameMode.Type newType = GameMode.Type.DEATH_MATCH;
            switch (mBindValue.getType()) {
                case DEATH_MATCH:
                    newType = GameMode.Type.HIT_TARGET;
                    break;
                case HIT_TARGET:
                    newType = GameMode.Type.CATCH_FLAG;
                    break;
                case CATCH_FLAG:
                    newType = GameMode.Type.DEATH_MATCH;
                    break;
                default:
                    newType = GameMode.Type.HIT_TARGET;
                    break;
            }
            mBindValue.setType(newType);
            update();
        }
    }

    private void update() {
        if(mBindValue == null)
            return;

        String text = mPrefix;
        String modeString = mDeathMatch;
        switch (mBindValue.getType()) {
            case DEATH_MATCH:
                modeString = mDeathMatch;
                break;
            case HIT_TARGET:
                modeString = mHitTarget;
                break;
            case CATCH_FLAG:
                modeString = mCatchFlag;
                break;
            default:
                break;
        }
        text += modeString;
        setText(text);
    }
}
