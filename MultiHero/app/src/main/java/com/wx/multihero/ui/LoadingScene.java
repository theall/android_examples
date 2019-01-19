package com.wx.multihero.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wx.multihero.R;
import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.Utils;
import com.wx.multihero.ui.widget.PrimitiveText;
import com.wx.multihero.ui.widget.ProgressBar;

public class LoadingScene extends BaseScene implements AssetsLoader.LoaderNotify {
    private ProgressBar mProgressBar;
    private PrimitiveText mLoadingText;

    public LoadingScene(SceneType sceneType, Notify notify) {
        super(sceneType, notify);
        mLoadingText = new PrimitiveText(0, null);
        mLoadingText.setText(Utils.getStringFromResourceId(R.string.loading));

        float pbWidth = mScreenRect.width() * Utils.GOLD_LINE;
        float pbHeight = pbWidth / 20;
        RectF rect = new RectF(0, 0, pbWidth, pbHeight);
        rect.offset((mScreenRect.width() - pbWidth) / 2, (mScreenRect.height() - pbHeight) / 2);
        mProgressBar = new ProgressBar(0, rect);
        mLoadingText.setColor(Color.WHITE);
        mLoadingText.moveTo((mScreenRect.width()-mLoadingText.getBoundingRect().width())/2,
                rect.top-mLoadingText.getBoundingRect().height()-Utils.getRealHeight(10));
    }

    public void render(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.BLACK);
        mLoadingText.render(canvas, paint);
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
