package com.wx.multihero;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.wx.multihero.base.SceneType;
import com.wx.multihero.base.SoundPlayer;
import com.wx.multihero.base.Utils;
import com.wx.multihero.entity.TriggersManager;
import com.wx.multihero.ui.BaseScene;
import com.wx.multihero.ui.CharacterChooseScene;
import com.wx.multihero.ui.GameScene;
import com.wx.multihero.ui.LoadingScene;
import com.wx.multihero.ui.MapChooseScene;
import com.wx.multihero.ui.TitleScene;
import com.wx.multihero.variability.Game;

public class MainView extends SurfaceView implements
		SurfaceHolder.Callback,
		Runnable,
		BaseScene.Notify
{
	public static Context context = null;
	private static final int FPS = 60;
	private static final float GRAVITY = 0.35f;

	private int mFps;
	private boolean mShowFPS = true;
	private int mFrameCounter;
	public static int screenWidth;
	public static int screenHeight;
	private SceneType mSceneState;
	private int mScore;
	private int mHighScore;
	private float mVelocity;
	private float mGravity;
	private boolean mFlyOver;

	private int mMapOffsetX;
	
	private final Paint mPaint = new Paint();
	private SurfaceHolder mSurfaceHolder;
	private Canvas mCanvas;
	
	private TriggersManager mTriggersManager = new TriggersManager();

	// scene
    private SceneStack mSceneStack = new SceneStack();
	private LoadingScene mLoadingScene;
	private TitleScene mTitleScene;
    private CharacterChooseScene mCharacterChooseScene;
	private MapChooseScene mMapChooseScene;
	private GameScene mGameScene;
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
		AssetsLoader.getInstance().asycLoad();
		mBigFont.loadAssets();
		mLoadingScene.loadAssets();
		mTitleScene.loadAssets();
        mCharacterChooseScene.loadAssets();
		mMapChooseScene.loadAssets();
		mGameScene.loadAssets();
	}
	
	private void init() {
		context = getContext();
		Utils.setContext(context);
		mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		Utils.setResolution(screenWidth, screenHeight);
		BaseScene.setResolution(screenWidth, screenHeight);
		mLoadingScene = new LoadingScene(SceneType.LOADING, this);
        AssetsLoader.getInstance().setConfigure(this.getContext(), SoundPlayer.initialize(), mLoadingScene);

        mTitleScene = new TitleScene(SceneType.TITLE, this);
        mCharacterChooseScene = new CharacterChooseScene(SceneType.CHARACTER, this);
        mMapChooseScene = new MapChooseScene(SceneType.MAP_CHOOSE, this);
		mGameScene = new GameScene(SceneType.GAME, this);
        mSceneStack.clearPush(mLoadingScene);
	}

	private void gameOver() {
		Game.getInstance().setState(Game.State.OVER);
		if (mScore > mHighScore) {
			mHighScore = mScore;
		}
	}

	public void update() {
		Game.getInstance().step();
	}

	private void initNewGame() {
		mScore = 0;
		mFlyOver = false;
		mGravity = GRAVITY;
		mMapOffsetX = 0;
		mVelocity = 0;
		mSceneState = SceneType.GAME;
		Game.getInstance().reset();
	}

	public Bundle saveState() {
		Bundle map = new Bundle();
		return map;
	}

	public void restoreState(Bundle icicle) {
		Game.getInstance().setState(Game.State.PAUSED);
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
            mCanvas.drawText(String.format("FPS:%2d", mFps),
					Utils.getRealWidth(10),
					Utils.getRealHeight(20),
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
		SceneType topSceneType = mSceneStack.getTopSceneType();
		SceneType topLessSceneType = mSceneStack.getTopLessSceneType();
		if(topSceneType == SceneType.TITLE) {

		} else if(topSceneType == SceneType.GAME) {
			Game.getInstance().earthQuake();
		}
		mSceneStack.processTouchEvent(event);
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
			System.exit(0);
        } else if (sceneType == SceneType.TITLE) {
			System.exit(0);
        } else if(sceneType == SceneType.CHARACTER) {
            mSceneStack.clearPush(mTitleScene);
        } else if(sceneType == SceneType.MAP_CHOOSE) {
            mSceneStack.clearPush(mCharacterChooseScene);
        } else if (sceneType == SceneType.GAME) {
			mSceneStack.clearPush(mMapChooseScene);
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
		} else if(sceneType == SceneType.MAP_CHOOSE) {
            Game.getInstance().loadPlayers(mCharacterChooseScene.getPlayerList());
        	Game.getInstance().loadMap(mMapChooseScene.getSelectedMap());
        	mSceneStack.clearPush(mGameScene);
        } else if(sceneType == SceneType.GAME) {

        } else if(sceneType == SceneType.OPTION) {

        } else if(sceneType == SceneType.OVER) {

        } else if(sceneType == SceneType.CREDIT) {

        } else {

        }
	}

	public void setGameState(Game.State state) {
		Game.getInstance().setState(state);
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
