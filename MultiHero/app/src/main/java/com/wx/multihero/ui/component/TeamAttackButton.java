package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.Button;

public class TeamAttackButton extends Button {
    private Boolean mBindValue;
    private String mPrefix;
    private String mOn;
    private String mOff;
    public TeamAttackButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
        mPrefix = Utils.getStringFromResourceId(R.string.team_attack) + "  ";
        mOn = Utils.getStringFromResourceId(R.string.on);
        mOff = Utils.getStringFromResourceId(R.string.off);
    }

    public void loadAssets() {
        Bitmap buttonBackground = AssetsLoader.getInstance().loadBitmap("gfx/ui/but_ta.png");
        setBitmaps(buttonBackground);
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
        update();
    }

    public void setBindValue(Boolean value) {
        mBindValue = value;
    }

    @Override
    public void touched() {
        if(mBindValue != null) {
            mBindValue = !mBindValue;
            update();
        }
    }

    private void update() {
        String text = mPrefix;
        if(mBindValue!=null && mBindValue) {
            text += mOn;
        } else {
            text += mOff;
        }
        setText(text);
    }
}
