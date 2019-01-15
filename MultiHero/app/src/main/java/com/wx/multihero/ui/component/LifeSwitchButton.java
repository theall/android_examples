package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.SwitchMenu;

public class LifeSwitchButton extends SwitchMenu {
    private String mPrefix;
    private Integer mBindValue;
    public LifeSwitchButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);

        mPrefix = Utils.getStringFromResourceId(R.string.lifes) + "  ";
    }

    @Override
    public void selected(int id, Bundle parameters) {
        super.selected(id, parameters);

        if(mBindValue != null) {
            if(id == SwitchMenu.ID_BUTTON_LEFT) {
                mBindValue--;
            } else {
                mBindValue++;
            }
        }
        update();
    }

    public void setBindValue(Integer value) {
        mBindValue = value;
        update();
    }

    private void update() {
        if(mBindValue != null) {
            String text = mPrefix;
            if(mBindValue<1)
                mBindValue = 99;
            else if(mBindValue>99)
                mBindValue = 1;
            text += mBindValue.toString();
            setText(text);
        }
    }

    public void loadAssets() {
        Bitmap buttonBackground = AssetsLoader.loadBitmap("gfx/ui/but_ta.png");
        Bitmap arrow1 = AssetsLoader.loadBitmap("gfx/ui/arrow1.png");
        Bitmap arrow2 = AssetsLoader.loadBitmap("gfx/ui/arrow2.png");
        setBitmaps(buttonBackground, arrow1, arrow2);
        setText(Utils.getStringFromResourceId(R.string.lifes));
        setTouchedSoundEffect(AssetsLoader.loadSound("sound/click.mp3"));
    }

}
