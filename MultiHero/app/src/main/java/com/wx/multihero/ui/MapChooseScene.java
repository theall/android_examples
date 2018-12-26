package com.wx.multihero.ui;

import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.Map;
import com.wx.multihero.entity.MapSet;
import com.wx.multihero.entity.MapSetManager;
import com.wx.multihero.ui.widget.PictureItem;
import com.wx.multihero.ui.widget.Text;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MapChooseScene extends BaseScene {
	private ArrayList<PictureItem> mMapThumbList = new ArrayList<PictureItem>();
    private Text mMapSetName;
    private static float SPACE_COLUMN = 20;
    private static float SPACE_ROW = 10;
	public MapChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);

        mMapSetName = new Text(0, new RectF());
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
	}

	public int processTouchEvent(MotionEvent event) {
		return 0;
	}

	public void shiftIn() {

	}

	public void shiftOut() {

	}

    public void loadAssets() {
        MapSetManager.load();
        MapSet mapSet = MapSetManager.getMapSet(1);
        if(mapSet != null) {
            setMapSet(mapSet);
        }
    }
}
