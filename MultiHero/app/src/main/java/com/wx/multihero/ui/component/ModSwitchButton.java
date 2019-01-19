package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.entity.ModManager;
import com.wx.multihero.ui.widget.SwitchMenu;

public class ModSwitchButton extends SwitchMenu {
    private ModManager mBindValue;

    public ModSwitchButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
    }

    @Override
    public void selected(int id, Bundle parameters) {
        super.selected(id, parameters);

        if(mBindValue != null) {
            if(id == SwitchMenu.ID_BUTTON_LEFT) {
                mBindValue.shiftBack();
            } else {
                mBindValue.shiftNext();
            }
            update();
        }
    }

    public void setBindValue(ModManager value) {
        mBindValue = value;
        update();
    }

    private void update() {
        if(mBindValue != null) {
            setText(mBindValue.getCurrentMod().getName());
        }
    }

    public void loadAssets() {
        Bitmap buttonBackground = AssetsLoader.getInstance().loadBitmap("gfx/ui/but_ta.png");
        Bitmap arrow1 = AssetsLoader.getInstance().loadBitmap("gfx/ui/arrow1.png");
        Bitmap arrow2 = AssetsLoader.getInstance().loadBitmap("gfx/ui/arrow2.png");
        setBitmaps(buttonBackground, arrow1, arrow2);
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
    }
}
