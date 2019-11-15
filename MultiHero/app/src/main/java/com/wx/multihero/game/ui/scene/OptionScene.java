/**
 * Copyright (C) Froggen, 850781307@qq.com
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.wx.multihero.game.ui.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import com.wx.multihero.R;
import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.ui.widget.BitmapText;
import com.wx.multihero.game.ui.widget.ColorProgressBar;
import com.wx.multihero.game.ui.widget.MenuItem;
import com.wx.multihero.game.ui.widget.PictureItem;
import com.wx.multihero.game.ui.widget.PrimitiveText;
import com.wx.multihero.game.ui.widget.BitmapSwitch;
import com.wx.multihero.game.ui.widget.TextSwitch;
import com.wx.multihero.game.ui.widget.TouchableWidget;
import com.wx.multihero.os.SoundPlayer;
import com.wx.multihero.os.TouchState;

import java.security.PrivateKey;
import java.util.ArrayList;

public class OptionScene extends BaseScene implements TouchableWidget.Callback {    //选项
    private PictureItem mBackgoundBitmap;    //后台位置图
    private PictureItem mTileBitmap;
    private PictureItem mbackward;
    private boolean mStaticMode = false;//这里是静态方法
    private ColorProgressBar mProgressBar;//设置步进栏颜色
    private PrimitiveText mOptionScene; //准备界面的主题
    private BitmapText fullScreenText;//全屏选项按钮绘制
    private TextSwitch fullScreenSwitch; //控件绘制
    private BitmapText VideoText;  //32位或者16位


    private BitmapText SfxText;  // 关闭所有音乐
    private BitmapText MusicText; //关闭背景音乐
    private BitmapText Controls; //用键盘或者用手柄玩
    private BitmapText Language;//这个是用什么语言玩
    private BitmapText English;//英语
    private BitmapText Back;//

    //private static final float MENU_DEFAULT_SPACE = 20.0f; //标题居中
    //private ArrayList<MenuItem> mMenuItems = new ArrayList<MenuItem>();//枚举
    private static int TAG_FULL_SCREEN = 1;

    public OptionScene(SceneType sceneType, Notify notify) {   //构造方法，场景类型，通告上级
        super(sceneType, notify);
        mBackgoundBitmap = new PictureItem(null);//创建背景图片对象
        //mTileBitmap = new PictureItem(null);
        mbackward = new PictureItem(null);

        //这里是用图片文本方式绘制选项按钮界面
        fullScreenText = new BitmapText(null);//创建选项按钮对象出来然后下面再进行绘制
        fullScreenSwitch = new TextSwitch(this, null);//这里是NEW出按钮对象出来
        fullScreenSwitch.setTag(TAG_FULL_SCREEN);//进行按钮调用

        VideoText = new BitmapText(null);//这里全是new出要绘制的对象
        SfxText = new BitmapText(null);
        MusicText = new BitmapText(null);
        Controls = new BitmapText(null);
        Language = new BitmapText(null);
        English = new BitmapText(null);
        Back = new BitmapText(null);
        //
        mOptionScene = new PrimitiveText(null);//P是Mo的类，这里是将Pr类NEW出来Mo调用构造函数
        mOptionScene.setText(Utils.getStringFromResourceId(R.string.loading));

        float pbWidth = mScreenRect.width() * Utils.GOLD_LINE;
        float pbHeight = pbWidth / 20;
        RectF rect = new RectF(0, 0, pbWidth, pbHeight);
        rect.offset((mScreenRect.width() - pbWidth) / 2, (mScreenRect.height() - pbHeight) / 2);
        mProgressBar = new ColorProgressBar(null); //new出颜色渐进栏对象方便调用
        mProgressBar.setBoundingRect(rect); //设置边界Rect
        mProgressBar.setColor(Color.GREEN, Color.RED);//设置步进栏颜色
        mOptionScene.setColor(Color.WHITE); //设置准备进入的背景颜色
        mOptionScene.moveTo((mScreenRect.width() - mOptionScene.getBoundingRect().width()) / 2,
                rect.top - mOptionScene.getBoundingRect().height() - Utils.getRealHeight(10)); //移动
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {//加载资源
        fullScreenText.setText("Full Screen"); //这里写的是要进行绘制的文本字符串
        VideoText.setText("Video");
        SfxText.setText("Sfx");
        MusicText.setText("Music");
        Controls.setText("Controls");
        Language.setText("Language");
        English.setText("English");
        Back.setText("BACk");
        float top = 180;//上
        float left = 200;//左边
        float optionLeft = 500;//这里是所有控件按钮所在的坐标
        fullScreenText.moveTo(left, top);//这里是选项按钮的坐标
        fullScreenSwitch.setOn(true);
        fullScreenSwitch.moveTo(optionLeft, top + 120); //控件的坐标
        VideoText.moveTo(20 + left, top + 40); //top+40是向下
        SfxText.moveTo(20 + left, top + 80);//left-20是左对齐
        MusicText.moveTo(20 + left, top + 120);
        Controls.moveTo(10 + left, top + 160);
        Language.moveTo(10 + left, top + 200);
        English.moveTo(optionLeft, top + 200);
        Back.moveTo(20 + left, top + 300);
        //这里是背景图片加载
        //mTileBitmap.setBitmap(AssetsLoader.getInstance().loadBitmap(String.format("gfx/ui/title.png")));//加载资源
        //mBackgoundBitmap.setBitmap(AssetsLoader.getInstance().loadBitmap(String.format("gfx/ui/bg.png")));//加载资源
        //mbackward.setBitmap(AssetsLoader.getInstance().loadBitmap(String.format("gfx/ui/backward.png")));//加载资源
        float screenWidth = mScreenRect.width();//这里写的是图片方法
        float screenHeight = mScreenRect.height();
        float titleStubHeight = screenHeight * (1 - Utils.GOLD_LINE); //黄金分割线

        if (mTileBitmap == null) //如果标题为空就执行后面绘制
        {
            mTileBitmap = new PictureItem(null); //这里new出想要的图片对象
            mTileBitmap.setBoundingRect(new RectF(0, 0, screenWidth, titleStubHeight));//高宽
            mTileBitmap.setBitmap(AssetsLoader.getInstance().loadBitmap("gfx/ui/title.png"));
            mTileBitmap.center();

        }
    }

    public boolean processTouchState(TouchState touchState) {
        fullScreenSwitch.processTouchState(touchState);
        return false;
    }

    public void render(Canvas canvas, Paint paint) { //绘制
        if (mStaticMode) {
            canvas.drawColor(Color.BLACK);              //这个render构造方法写的是调用P，C方法，进行描绘界面颜色
            mOptionScene.render(canvas, paint);  //背景渲染
            mProgressBar.render(canvas, paint);
        }
        mBackgoundBitmap.render(canvas, paint);//背景图片渲染
        mTileBitmap.render(canvas, paint);
        mbackward.render(canvas, paint);

        fullScreenText.render(canvas, paint); //每一个控件都用文本方式绘制然后渲染
        fullScreenSwitch.render(canvas, paint);//按钮绘制

        VideoText.render(canvas, paint);
        SfxText.render(canvas, paint);
        MusicText.render(canvas, paint);
        Controls.render(canvas, paint);
        Language.render(canvas, paint);
        English.render(canvas, paint);
        Back.render(canvas, paint);
    }

    public void step() {

    }

    public void selected(int id, Bundle parameters) { //这里是绘制图片的方法
        if (TAG_FULL_SCREEN == id) {
            boolean isOn = fullScreenSwitch.isOn();
            isOn = !isOn;
            fullScreenSwitch.setOn(isOn);
            SoundPlayer soundPlayer = SoundPlayer.getInstance();
            if (isOn) {
                soundPlayer.resumeAudio();
            } else {
                soundPlayer.pauseAudio();
            }
        }
    }
}
