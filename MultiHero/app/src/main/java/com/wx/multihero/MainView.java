package com.wx.multihero;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.BigFont;
import com.wx.multihero.base.GameState;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.entity.TriggersManager;
import com.wx.multihero.ui.BaseScene;
import com.wx.multihero.ui.LoadingScene;
import com.wx.multihero.ui.TitleScene;

public class MainView extends SurfaceView implements
		SurfaceHolder.Callback,
		Runnable,
		TitleScene.Callback,
		BaseScene.Notify
{
	public static Context context = null;
	private static final int FPS = 90;
	private static final float GRAVITY = 0.35f;
	private static final Random RND = new Random();

	private int mFps;
	private boolean mShowFPS = true;
	private int mFrameCounter;
	public static int screenWidth;
	public static int screenHeight;
	private GameState mGameState;
	private SceneType mSceneState;
	private int mScore;
	private int mHighScore;
	private float mVelocity;
	private float mGravity;
	private boolean mFlyOver;
	private boolean mAutoPilot = true;

	private int mMapOffsetX;
	
	private final Paint mPaint = new Paint();
	private AssetsLoader mAssetsLoader;
	private SurfaceHolder mSurfaceHolder;
	private Canvas mCanvas;
	private int mSndTitle;
	
	private TriggersManager mTriggersManager = new TriggersManager();

	// scene
    private SceneStack mSceneStack = new SceneStack();
	private LoadingScene mLoadingScene;
	private TitleScene mTitleScene;
	private BigFont mBigFont = new BigFont();

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
		mAssetsLoader.asycLoad();
		mBigFont.loadAssets();
		mTitleScene.loadAssets();
		
		// sound resources
		mSndTitle = AssetsLoader.loadSound("title");
	}
	
	private void init() {
		context = getContext();
		mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        
        screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		BaseScene.setResolution(screenWidth, screenHeight);
		mLoadingScene = new LoadingScene(SceneType.LOADING, this);
        mTitleScene = new TitleScene(this, SceneType.TITLE, this);

        mSceneStack.clearPush(mLoadingScene);
		mAssetsLoader = new AssetsLoader(this.getContext(), SoundPlayer.initialize(), mLoadingScene);
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
		mSceneState = SceneType.MAIN;
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
		screenWidth = w;
		screenHeight = h;
	}

	private void drawFPS() {
        if(mCanvas != null) {
            int oldColor = mPaint.getColor();
            mPaint.setColor(Color.GREEN);
            String text = String.format("FPS:%2d", mFps);
            mCanvas.drawText(text,
					10,
					10,
					mPaint);
            mPaint.setColor(oldColor);
        }
    }

	private void drawing() {

		try {
			mCanvas = mSurfaceHolder.lockCanvas();
			if(mCanvas==null)
				return;

			mSceneStack.render(mCanvas, mPaint);

			if(mShowFPS)
            {
                drawFPS();
            }
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
        mSceneStack.processTouchEvent(event);
		SceneType topSceneType = mSceneStack.getTopSceneType();
		SceneType topLessSceneType = mSceneStack.getTopLessSceneType();
		if(topSceneType == SceneType.TITLE) {

		}
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
		return true;
	}

	public void run() {
		try {
			long time = System.currentTimeMillis();
			while (true) {
				update();
				drawing();
				Thread.sleep(1);
				mFrameCounter++;
				long diff = System.currentTimeMillis() - time;
                mFps = (int)((float)mFrameCounter*1000/diff);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
	    new Thread(this).start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

	public void menuSelected(TitleScene.MenuID id) {
		switch (id) {
			case ADV:
				break;
			case VS:
				break;
			case EXIT:
				System.exit(0);
				break;
			default:
				break;
		}
	}

	public void requestExit(SceneType sceneType) {
		if(sceneType == SceneType.LOADING) {
			mSceneStack.clearPush(mTitleScene);
			SoundPlayer.playAudio(mSndTitle);
		}
	}
}
