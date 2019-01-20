package com.wx.multihero.ui.component;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.ui.widget.Button;
import com.wx.multihero.variability.Player;

public class PlayerTypeButton extends Button implements Player.TypeChangedCallback {
    private Bitmap mActorCpu;
    private Bitmap mActorHumen;
    private Bitmap mActorUnknown;
    private Player mBindValue;

    public PlayerTypeButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);
    }

    @Override
    public void touched() {
        super.touched();
        if(mBindValue != null) {
            Player.Type newType = Player.Type.UNKNOWN;
            Player.Type playerType = mBindValue.getType();
            switch (playerType) {
                case CPU:
                    newType = Player.Type.HUM;
                    break;
                case HUM:
                    newType = Player.Type.UNKNOWN;
                    break;
                case UNKNOWN:
                    newType = Player.Type.CPU;
                    break;
                default:
                    break;
            }
            mBindValue.setType(newType);
            update();
        }
    }

    public void setBindValue(Player value) {
        if(mBindValue != null) {
            mBindValue.setTypeChangedCallback(null);
        }
        mBindValue = value;
        if(mBindValue != null) {
            mBindValue.setTypeChangedCallback(this);
        }
        update();
    }

    private void update() {
        Bitmap bitmap;
        if(mBindValue != null) {
            Player.Type playerType = mBindValue.getType();
            switch (playerType) {
                case CPU:
                    bitmap = mActorCpu;
                    break;
                case HUM:
                    bitmap = mActorHumen;
                    break;
                case UNKNOWN:
                default:
                    bitmap = mActorUnknown;
                    break;
            }
        } else {
            bitmap = mActorUnknown;
        }
        setBitmap(bitmap);
    }

    public void loadAssets() {
        mActorCpu = AssetsLoader.getInstance().loadBitmap("gfx/ui/butCPU.png");
        mActorHumen = AssetsLoader.getInstance().loadBitmap("gfx/ui/butHum.png");
        mActorUnknown = AssetsLoader.getInstance().loadBitmap("gfx/ui/butNA.png");
        setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));
        update();
    }

    public void typeChanged(Player.Type oldType, Player.Type newType) {
        update();
    }
}
