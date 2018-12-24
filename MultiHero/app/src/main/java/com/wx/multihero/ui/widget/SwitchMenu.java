package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.MultiAutoCompleteTextView;

import com.wx.multihero.base.AssetsLoader;

public class SwitchMenu extends Widget {
    private Text mText;
    private Button mBtnLeft;
    private Button mBtnRight;
    public enum ButtonType {
        LEFT,
        RIGHT
    }
    public SwitchMenu(int id, RectF boundingRect) {
        super(id, boundingRect);

        //Bitmap leftBitmap = AssetsLoader.loadBitmap("");
        //mBtnLeft = new Button(ButtonType.LEFT.ordinal(), );
        //mText = new Text();
    }

    public void render(Canvas canvas, Paint paint) {

    }
}
