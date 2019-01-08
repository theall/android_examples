package com.wx.multihero.ui.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import com.wx.multihero.R;
import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.ColorButton;
import com.wx.multihero.ui.widget.PrimitiveText;
import com.wx.multihero.ui.widget.TouchableWidget;

public class TeamButton extends TouchableWidget implements Renderable,TouchableWidget.Callback{
    private PrimitiveText mLabel;
    private PrimitiveText mTextNone;
    private ColorButton mBtnColor;

    public TeamButton(int id, RectF boundingRect, Callback callback) {
        super(id, boundingRect, callback);

        mLabel = new PrimitiveText(0, boundingRect);
        mTextNone = new PrimitiveText(0, boundingRect);
        mBtnColor = new ColorButton(0, boundingRect, this);
    }

    public void loadAssets() {
        RectF rect = new RectF();
        rect.left = 0;
        rect.top = 0;
        rect.right = Utils.getRealWidth(96);
        rect.bottom = Utils.getRealHeight(20);
        setBoundingRect(rect);

        rect.right /= 2;
        mLabel.setBoundingRect(rect);
        mLabel.setText(Utils.getStringFromResourceId(R.string.team));

        rect.offsetTo(rect.right, rect.top);
        mTextNone.setBoundingRect(rect);

        float paddingH = Utils.getRealWidth(2);
        float paddingV = Utils.getRealHeight(3);
        rect.left += paddingH;
        rect.right -= paddingH;
        rect.top += paddingV;
        rect.bottom -= paddingV;
        mBtnColor.setBoundingRect(rect);
    }

    public void positionChanged(float dx, float dy) {
        mLabel.offset(dx, dy);
        mTextNone.offset(dx, dy);
        mBtnColor.offset(dx, dy);
    }

    public void render(Canvas canvas, Paint paint) {
        if(canvas == null)
            return;

        int oldColor = paint.getColor();
        paint.setColor(0xffC8C8C8);
        canvas.drawRect(mBoundingRect, paint);
        paint.setColor(oldColor);
        mLabel.render(canvas, paint);
        mTextNone.render(canvas, paint);
        mBtnColor.render(canvas, paint);
    }

    public void selected(int id, Bundle parameters) {
        mBtnColor.setColor(Color.RED);
    }
}
