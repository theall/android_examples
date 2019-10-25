/**
 * Copyright (C) Bilge Theall, wazcd_1608@qq.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.wx.multihero;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wx.multihero.game.Game;
import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.os.KeyboardState;
import com.wx.multihero.os.SoundPlayer;
import com.wx.multihero.os.TouchState;

public class MainView extends SurfaceView implements
		SurfaceHolder.Callback,
		Runnable
{
	public static Context context = null;
	private final Paint mPaint = new Paint();
	private SurfaceHolder mSurfaceHolder;
	private Canvas mCanvas;

	private Game mGame;

	private class PendingTouch {
		boolean actived;
		float x;
		float y;
		TouchState.Action action;
	}

	PendingTouch mPendingTouch = new PendingTouch();

	public MainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init();
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}
	
	private void init() {
		mPendingTouch.actived = false;
		context = getContext();
		Utils.setContext(context);

		AssetsLoader.getInstance().setAssetManager(this.getContext().getAssets());
		AssetsLoader.getInstance().setSoundPool(SoundPlayer.getInstance().getSoundPool());

		mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

		mGame = new Game();
        int scrWidth = getResources().getDisplayMetrics().widthPixels;
		int scrHeight = getResources().getDisplayMetrics().heightPixels;
		int screenWidth = Math.max(scrWidth, scrHeight);
		int screenHeight = Math.min(scrWidth, scrHeight);
		mGame.setResolution(screenWidth, screenHeight);
		mGame.start();
		Log.i("multihero", "Resolution:" + screenWidth + " " + screenHeight);
	}

	public void update() {
		TouchState ts = TouchState.getInstance();
		TouchState.Action touchAction = TouchState.Action.NONE;
		if(mPendingTouch.actived) {
			mPendingTouch.actived = false;
			ts.mPos.x = mPendingTouch.x;
			ts.mPos.y = mPendingTouch.y;
			touchAction = mPendingTouch.action;
		}
		ts.setAction(touchAction);
		mGame.step();
	}

	public Bundle saveState() {
		Bundle map = new Bundle();
		return map;
	}

	public void restore(Bundle icicle) {

	}

	public void pause() {

	}

	public void start() {

	}

	public void resume() {

	}

	public void resume(Bundle mao) {

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mGame.setResolution(w, h);
	}

	private void drawing() {
		try {
			mCanvas = mSurfaceHolder.lockCanvas();
			if(mCanvas==null)
				return;
			mGame.render(mCanvas, mPaint);
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            mCanvas = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				mCanvas = null;
            }
        }
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		int action = event.getAction();
		TouchState.Action touchAction = TouchState.Action.NONE;
		if(action == MotionEvent.ACTION_DOWN) {
			touchAction = TouchState.Action.PRESSED;
		} else if(action == MotionEvent.ACTION_UP) {
			touchAction = TouchState.Action.RELEASED;
		}
		mPendingTouch.action = touchAction;
		mPendingTouch.x = x;
		mPendingTouch.y = y;
		mPendingTouch.actived = true;
		Log.i("event", event.toString());
		return true;
	}

	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			long lastFrameStartTime = startTime;
			int counter = 0;
			int sleepSeconds = 1;
			while (true) {
				long currentFrameStartTime = System.currentTimeMillis();
				update();
				drawing();
				counter++;
				float realTimeFps = 1000.0f / (currentFrameStartTime - lastFrameStartTime);
				lastFrameStartTime = currentFrameStartTime;
				int targetFps = mGame.getTargetFps();
				int fpsDiff = Math.round(realTimeFps - targetFps);
				if(fpsDiff>1 && sleepSeconds<33) {
					sleepSeconds++;
				} else if(fpsDiff<-1 && sleepSeconds>1){
					sleepSeconds--;
				}
				if(currentFrameStartTime - startTime > 1000) {
					startTime = currentFrameStartTime;
					mGame.setCurrentFps((int)realTimeFps);
				}
				Thread.sleep(sleepSeconds);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
	    new Thread(this).start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mGame.setResolution(width, height);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		KeyboardState.getInstance().setKeyState(keyCode, true);

		if(Utils.DEBUG) {
			if(keyCode == KeyEvent.KEYCODE_SPACE) {
				mGame.setTargetFps(1);
			} else if(keyCode == KeyEvent.KEYCODE_P) {
				mGame.setTargetFps(60);
			}
		}
		return false;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		KeyboardState.getInstance().setKeyState(keyCode, false);
		if(keyCode == KeyEvent.KEYCODE_BACK)
			mGame.back();
		return false;
	}

	private void loadConfig() {
		Context ctx = getContext();
		SharedPreferences sharedPreferences = ctx.getSharedPreferences(ctx.getPackageName(), ctx.MODE_PRIVATE);
	}

	private void saveConfig() {
		Context ctx = getContext();
		SharedPreferences sharedPreferences = ctx.getSharedPreferences(ctx.getPackageName(), ctx.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("music", true);
		editor.putBoolean("sound", true);
		editor.commit();
	}
}
