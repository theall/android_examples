package com.wx.multihero;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import android.R.integer;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	public static Context context = null;
	private static final int FPS = 90;
	private static final float GRAVITY = 0.35f;
	private static final Random RND = new Random();

	private int mFrameCounter;
	private int mScreenWidth;
	private int mScreenHeight;
	private GameState mGameState;
	private SceneState mSceneState;
	private int mScore;
	private int mHighScore;
	private float mVelocity;
	private float mGravity;
	private boolean mFlyOver;
	private boolean mAutoPilot = true;

	private int mMapOffsetX;
	
	private final Paint mPaint = new Paint();
	private SurfaceHolder mSurfaceHolder;
	private Canvas mCanvas;
	private SoundPool mSoundPool;
	private int mSndTitle;
	
	private TriggersManager mTriggersManager = new TriggersManager();
	private MainMenu mMainMenu;
	private BigFont mBigFont = new BigFont();
	
	private void playSoundLoop(int id) {
		mSoundPool.play(id, 1.0f, 1.0f, 100, -1, 1f);
	}
	
	private void playSound(int id) {
		mSoundPool.play(id, 1.0f, 1.0f, 100, 0, 1f);
	}

	public MainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init();
		loadAssets();
		initNewGame();
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
		loadAssets();
		initNewGame();
	}

	private void loadAssets() {
		
		mBigFont.loadAssets();
		mMainMenu.loadAssets();
		
		// sound resources
		mSoundPool = new SoundPool(400, AudioManager.STREAM_MUSIC, 100);
		mSndTitle = mSoundPool.load(context, R.raw.title, 1);
	}
	
	private void init() {
		context = getContext();
		mSurfaceHolder = getHolder();//得到SurfaceHolder对象
        mSurfaceHolder.addCallback(this);//注册SurfaceHolder
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);//保持屏幕长亮
        
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
		mScreenHeight = getResources().getDisplayMetrics().heightPixels;
		
        mMainMenu = new MainMenu(mScreenWidth, mScreenHeight);
	}

	private void autopilot() {
		if (!mAutoPilot)
			return;	
	}

	private void gameOver() {
		mGameState = GameState.GAMEOVER;
		if (mScore > mHighScore) {
			mHighScore = mScore;
		}
	}

	public void update() {
		if (mGameState == GameState.RUNNING || mGameState == GameState.GAMEOVER) {
			
		}

		if (mGameState == GameState.RUNNING) {
			autopilot();
		}
	}

	private void initNewGame() {
		mScore = 0;
		mFlyOver = false;
		mGravity = GRAVITY;
		mMapOffsetX = 0;
		mVelocity = 0;
		
		mGameState = GameState.READY;
		mSceneState = SceneState.MAIN;
		playSound(mSndTitle);
	}

	public GameState getGameState() {
		return mGameState;
	}

	public void setGameState(GameState gameState) {
		mGameState = gameState;
	}

	public Bundle saveState() {
		Bundle map = new Bundle();
		return map;
	}

	public void restoreState(Bundle icicle) {
		setGameState(GameState.PAUSED);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mScreenWidth = w;
		mScreenHeight = h;
	}

	private void drawing() {

		try {
			mCanvas = mSurfaceHolder.lockCanvas();
			if(mCanvas==null)
				return;
			
			mMainMenu.render(mCanvas, mPaint);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		playSoundLoop(mSndTitle);
		if(mGameState==GameState.READY)
        {
            mGameState = GameState.RUNNING;
        }
        else if(mGameState==GameState.RUNNING)
        {
        	if(mAutoPilot) {
        		mAutoPilot = false;
        	}
        }
        else if(mGameState==GameState.GAMEOVER)
        {
            float x = event.getX();
            float y = event.getY();
            
        }
		return super.onTouchEvent(event);
	}

	public void run() {
		try {
			long time = System.currentTimeMillis();
			int execCount = 0;
			float fps = 0;
			while (true) {
				update();
				drawing();
				mFrameCounter += 1;
				Thread.sleep(1000/FPS);
				execCount++;
				long diff = System.currentTimeMillis() - time;
				fps = (float)execCount*1000/diff;
				Log.i("fps", String.format("%f", fps));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		new Thread(this).start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mScreenWidth = width;
		mScreenHeight = height;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}
