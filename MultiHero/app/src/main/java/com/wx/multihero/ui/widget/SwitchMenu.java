package com.wx.multihero.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.MultiAutoCompleteTextView;

import com.wx.multihero.base.AssetsLoader;

public class SwitchMenu extends TouchableWidget implements TouchableWidget.Callback{
    private Text mText;
    private Button mBtnLeft;
    private Button mBtnRight;
    private PictureItem mBackground;

    public enum ButtonType {
        LEFT,
        RIGHT
    }
    private static final int ID_TEXT = 1;
    private static final int ID_BUTTON_LEFT = 2;
    private static final int ID_BUTTON_RIGHT = 3;

    public SwitchMenu(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);

        mText = new Text(ID_TEXT, null);
        mBtnLeft = new Button(ID_BUTTON_LEFT, null, this);
        mBtnRight = new Button(ID_BUTTON_RIGHT, null, this);
        mBackground = new PictureItem(0, null, null);
    }

    public Button getLeftButton() {
        return mBtnLeft;
    }

    public Button getRightButton() {
        return mBtnRight;
    }

    public void selected(int id, Bundle parameters) {

    }

    public void render(Canvas canvas, Paint paint) {
        mBackground.render(canvas, paint);
        mText.render(canvas, paint);
        mBtnLeft.render(canvas, paint);
        mBtnRight.render(canvas, paint);
    }
}
