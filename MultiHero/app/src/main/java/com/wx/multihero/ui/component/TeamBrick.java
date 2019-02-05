package com.wx.multihero.ui.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.Renderable;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.ColorButton;
import com.wx.multihero.ui.widget.PrimitiveText;
import com.wx.multihero.ui.widget.TouchableWidget;
import com.wx.multihero.ui.widget.Widget;
import com.wx.multihero.variability.Sprite.Player;

public class TeamBrick extends Widget implements Renderable,TouchableWidget.Callback{
    private PrimitiveText mLabel;
    private PrimitiveText mTextNone;
    private ColorButton mBtnColor;
    private Player mBindValue;
    private boolean mRenderRect;

    public TeamBrick(int id, RectF boundingRect) {
        super(id, boundingRect);

        mLabel = new PrimitiveText(0, boundingRect);
        mTextNone = new PrimitiveText(0, boundingRect);
        mBtnColor = new ColorButton(0, boundingRect, this);
        mRenderRect = false;
    }

    public void setBindValue(Player value) {
        mBindValue = value;
        update();
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

        rect.offset(rect.right, 0);
        float paddingH = Utils.getRealWidth(2);
        float paddingV = Utils.getRealHeight(3);
        rect.left += paddingH;
        rect.right -= paddingH;
        rect.top += paddingV;
        rect.bottom -= paddingV;
        mBtnColor.setBoundingRect(rect);
        mBtnColor.setTouchedSoundEffect(AssetsLoader.getInstance().loadSound("sound/click.mp3"));

        mTextNone.setBoundingRect(rect);
        mTextNone.setText(Utils.getStringFromResourceId(R.string.none));
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
        if(mRenderRect) {
            mBtnColor.render(canvas, paint);
        } else {
            mTextNone.render(canvas, paint);
        }
    }

    public void selected(int id, Bundle parameters) {
        if(mBindValue != null) {
            Player.Team team = mBindValue.getTeam();
            Player.Team newTeam = Player.Team.NONE;
            switch (team) {
                case RED:
                    newTeam = Player.Team.GREEN;
                    break;
                case BLUE:
                    newTeam = Player.Team.NONE;
                    break;
                case GREEN:
                    newTeam = Player.Team.BLUE;
                    break;
                case NONE:
                default:
                    newTeam = Player.Team.RED;
                    break;
            }
            mBindValue.setTeam(newTeam);
            update();
        }
    }

    private void update() {
        if(mBindValue != null) {
            Player.Team team = mBindValue.getTeam();
            int color = Color.BLACK;
            switch (team) {
                case RED:
                    color = Color.RED;
                    break;
                case BLUE:
                    color = Color.BLUE;
                    break;
                case GREEN:
                    color = Color.GREEN;
                    break;
                case NONE:
                default:
                    break;
            }
            mBtnColor.setColor(color);
            mRenderRect = color!=Color.BLACK;
        } else {
            mRenderRect = false;
        }
    }

    public boolean processTouchEvent(MotionEvent event) {
        mBtnColor.processTouchEvent(event);
        return false;
    }
}
