package com.wx.multihero.ui;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Map;
import com.wx.multihero.entity.MapSet;
import com.wx.multihero.entity.Mod;
import com.wx.multihero.entity.ModManager;
import com.wx.multihero.ui.widget.Button;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.Text;
import com.wx.multihero.ui.widget.TouchableWidget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MapChooseScene extends BaseScene implements TouchableWidget.Callback {
	private ArrayList<PictureItem> mMapThumbList = new ArrayList<PictureItem>();
    private Text mMapSetName;
    private Button mBtnBack;
    private static float SPACE_COLUMN = 20;
    private static float SPACE_ROW = 10;
    private final int ID_BACK = 1;
	public MapChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);

        mMapSetName = new Text(0, new RectF());
        mBtnBack = new Button(ID_BACK, null, this);
	}

	private void setMapSet(MapSet mapSet) {
	    if(mapSet == null)
	        return;

        mMapSetName.setText(mapSet.getName());

        mMapThumbList.clear();
        if(mapSet.getMapCount() < 1)
            return;

        float screenWidth = mScreenRect.width();
        float screenHeight = mScreenRect.height();
        float validWidth = screenWidth * Utils.GOLD_LINE;
        float validHeight = screenHeight * Utils.GOLD_LINE;
        float spaceColumn = SPACE_COLUMN * Utils.BASE_SCREEN_WIDTH / screenWidth;
        float spaceRow = SPACE_ROW * Utils.BASE_SCREEN_HEIGHT / screenHeight;
        float leftBound = (screenWidth - validWidth) / 2;
        float rightBound = leftBound + validWidth;
        int col = 0;
        int row = 0;
        RectF r = new RectF();
        r.left = leftBound;
        r.top = (screenHeight - validHeight) / 2;
        for(Map map : mapSet.getMapList()) {
            Bitmap thumb = map.getThumbBitmap();
            if(thumb == null)
                continue;
            float bmpWidth = thumb.getWidth();
            r.right = r.left + bmpWidth;
            r.bottom = r.top + thumb.getHeight();
            PictureItem pi = new PictureItem(row<<16+col, r, thumb);
            r.left = r.right + spaceColumn;
            if(r.left > rightBound) {
                r.left = leftBound;
                r.top += thumb.getHeight() + spaceRow;
                col = 0;
                row++;
            } else {
                col++;
            }
            mMapThumbList.add(pi);
        }
    }

	public void render(Canvas canvas, Paint paint) {
	    canvas.drawColor(Color.BLUE);
		for(PictureItem pi : mMapThumbList) {
		    pi.render(canvas, paint);
        }
        mBtnBack.render(canvas, paint);
	}

	public int processTouchEvent(MotionEvent event) {
        mBtnBack.processTouchEvent(event);
		return 0;
	}

	public void shiftIn() {

	}

	public void shiftOut() {

	}

    public void loadAssets() {
        ModManager.load();
        Mod mod = ModManager.getMod(1);
        if(mod != null) {
            setMapSet(mod.getAdvMaps());
        }

        Bitmap backBitmap = AssetsLoader.loadBitmap("gfx/ui/backward.png");
        RectF r = new RectF();
        r.left = Utils.getRealWidth(10);
        r.top = mScreenRect.bottom - backBitmap.getHeight() - Utils.getRealHeight(40);
        r.right = r.left + backBitmap.getWidth();
        r.bottom = r.top + backBitmap.getHeight();
        mBtnBack.setBoundingRect(r);
        mBtnBack.setBitmaps(backBitmap, backBitmap);
    }

    public void selected(int id, Bundle parameters) {
        if(mNotify != null) {
            if(id == ID_BACK) {
                mNotify.back(mSceneType);
            } else {
                mNotify.next(mSceneType, 0);
            }
        }
    }
}
