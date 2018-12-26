package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.ProgressBar;

public class LoadingScene extends BaseScene implements AssetsLoader.LoaderNotify {
    private ProgressBar mProgressBar;
    private float mTextLeft;
    private float mTextTop;
    public LoadingScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);

        float pbWidth = mScreenRect.width() * Utils.GOLD_LINE;
        float pbHeight = pbWidth / 20;
        RectF rect = new RectF(0, 0, pbWidth, pbHeight);
        rect.offset((mScreenRect.width() - pbWidth) / 2, (mScreenRect.height() - pbHeight) / 2);
        mProgressBar = new ProgressBar(0, rect);
        mTextLeft = 400;
        mTextTop = rect.top - 20;
    }

    public void render(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.GRAY);
        canvas.drawText("Loading...", mTextLeft, mTextTop, paint);
        mProgressBar.render(canvas, paint);
    }

    public int processTouchEvent(MotionEvent event) {
        return 0;
    }

    public void onProgress(int loadedSize, int totalSize) {
        mProgressBar.setProgress((float)loadedSize / totalSize);
        if(loadedSize==totalSize && mNotify!=null) {
            mNotify.next(mSceneType, 0);
        }
    }

    public void shiftIn() {

    }

    public void shiftOut() {

    }

    public void loadAssets() {

    }
}
