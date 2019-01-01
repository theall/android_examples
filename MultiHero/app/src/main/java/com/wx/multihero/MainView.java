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
import android.widget.Toast;

import com.wx.multihero.base.AssetsLoader;
import com.wx.multihero.base.BigFont;
import com.wx.multihero.base.GameState;
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.entity.MapSetManager;
import com.wx.multihero.entity.TriggersManager;
import com.wx.multihero.ui.BaseScene;
import com.wx.multihero.ui.CharacterChooseScene;
import com.wx.multihero.ui.LoadingScene;
import com.wx.multihero.ui.MapChooseScene;
import com.wx.multihero.ui.TitleScene;

public class MainView extends SurfaceView implements
		SurfaceHolder.Callback,
		Runnable,
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
	
	private TriggersManager mTriggersManager = new TriggersManager();

	// scene
    private SceneStack mSceneStack = new SceneStack();
	private LoadingScene mLoadingScene;
	private TitleScene mTitleScene;
    private CharacterChooseScene mCharacterChooseScene;
	private MapChooseScene mMapChooseScene;
	private BigFont mBigFont = new BigFont();
    private MapSetManager mMapSetManager;

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
        mCharacterChooseScene.loadAssets();
		mMapChooseScene.loadAssets();
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
        mAssetsLoader = new AssetsLoader(this.getContext(), SoundPlayer.initialize(), mLoadingScene);

        mTitleScene = new TitleScene(SceneType.TITLE, this);
        mCharacterChooseScene = new CharacterChooseScene(SceneType.CHARACTER, this);
        mMapChooseScene = new MapChooseScene(SceneType.MAP_CHOOSE, this);
        mSceneStack.clearPush(mLoadingScene);
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
		mSceneState = SceneType.GAME;
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

	public void back() {
	    back(mSceneStack.getTopSceneType());
    }
	public void back(SceneType sceneType) {
        if (sceneType == SceneType.LOADING) {

        } else if (sceneType == SceneType.TITLE) {

        } else if(sceneType == SceneType.CHARACTER) {
            mSceneStack.clearPush(mTitleScene);
        } else if(sceneType == SceneType.MAP_CHOOSE) {
            mSceneStack.clearPush(mCharacterChooseScene);
        } else if (sceneType == SceneType.GAME) {

        } else if (sceneType == SceneType.OPTION) {

        } else if (sceneType == SceneType.OVER) {

        } else if (sceneType == SceneType.CREDIT) {

        } else {

        }
    }

	public void next(SceneType sceneType, int parameter) {
        if(sceneType == SceneType.LOADING) {
            mSceneStack.clearPush(mTitleScene);
        } else if(sceneType == SceneType.TITLE) {
            TitleScene.MenuID id = TitleScene.MenuID.values()[parameter];
            switch (id) {
                case ADV:
                    mSceneStack.clearPush(mCharacterChooseScene);
                    break;
                case VS:
                    mSceneStack.clearPush(mCharacterChooseScene);
                    break;
                case OPTION:
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        } else if(sceneType == SceneType.CHARACTER) {
            mSceneStack.clearPush(mMapChooseScene);
        } else if(sceneType == SceneType.GAME) {

        } else if(sceneType == SceneType.OPTION) {

        } else if(sceneType == SceneType.OVER) {

        } else if(sceneType == SceneType.CREDIT) {

        } else {

        }
	}
}
