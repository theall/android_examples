package com.wx.multihero.ui;

import com.wx.multihero.base.SceneType;
import com.wx.multihero.ui.widget.PictureItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MapChooseScene extends BaseScene {
	private ArrayList<PictureItem> mMapThumbList = new ArrayList<PictureItem>();

	public MapChooseScene(SceneType sceneType, Notify notify) {
		super(sceneType, notify);

	}

	public void render(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		
	}

	public int processTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

}
