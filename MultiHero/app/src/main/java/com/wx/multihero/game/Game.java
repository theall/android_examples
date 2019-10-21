package com.wx.multihero.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wx.multihero.game.base.AssetsLoader;
import com.wx.multihero.game.base.BigFont;
import com.wx.multihero.game.base.GameState;
import com.wx.multihero.game.base.Renderable;
import com.wx.multihero.game.base.SceneType;
import com.wx.multihero.game.base.Stepable;
import com.wx.multihero.game.base.Utils;
import com.wx.multihero.game.ui.BaseScene;
import com.wx.multihero.game.ui.CharacterChooseScene;
import com.wx.multihero.game.ui.GameScene;
import com.wx.multihero.game.ui.LoadingScene;
import com.wx.multihero.game.ui.MapChooseScene;
import com.wx.multihero.game.ui.TitleScene;
import com.wx.multihero.os.TouchState;

public class Game implements BaseScene.Notify, Renderable, Stepable {
    private static final int FPS = 60;
    private int mFps;
    private boolean mShowFPS = true;

    // scene
    private SceneStack mSceneStack = new SceneStack();
    private LoadingScene mLoadingScene;
    private TitleScene mTitleScene;
    private CharacterChooseScene mCharacterChooseScene;
    private MapChooseScene mMapChooseScene;
    private GameScene mGameScene;
    private BigFont mBigFont = new BigFont();

    public Game() {

    }

    public void start() {
        init();
        loadAssets();
    }
    
    private void init() {
        mLoadingScene = new LoadingScene(SceneType.LOADING, this);
        AssetsLoader.getInstance().setLoadNotify(mLoadingScene);

        mTitleScene = new TitleScene(SceneType.TITLE, this);
        mCharacterChooseScene = new CharacterChooseScene(SceneType.CHARACTER, this);
        mMapChooseScene = new MapChooseScene(SceneType.MAP_CHOOSE, this);
        mGameScene = new GameScene(SceneType.GAME, this);
        mSceneStack.clearPush(mLoadingScene);
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

    public void setResolution(int width, int height) {
        Utils.setResolution(width, height);
        BaseScene.setResolution(width, height);
    }

    private void drawFPS(Canvas canvas, Paint paint) {
        if(canvas != null) {
            int oldColor = paint.getColor();
            paint.setColor(Color.GREEN);
            canvas.drawText(String.format("FPS:%2d", mFps),
                    Utils.getRealWidth(10),
                    Utils.getRealHeight(20),
                    paint);
            paint.setColor(oldColor);
        }
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
            mGameScene.setMap(mMapChooseScene.getSelectedMap(), mCharacterChooseScene.getPlayerList());
            mSceneStack.clearPush(mGameScene);
        } else if(sceneType == SceneType.GAME) {

        } else if(sceneType == SceneType.OPTION) {

        } else if(sceneType == SceneType.OVER) {

        } else if(sceneType == SceneType.CREDIT) {

        } else {

        }
    }

    public void setGameState(GameState state) {

    }

    public void render(Canvas canvas, Paint paint) {
        mSceneStack.render(canvas, paint);

        if(mShowFPS)
        {
            drawFPS(canvas, paint);
        }
    }

    public void step() {
        SceneType topSceneType = mSceneStack.getTopSceneType();
        SceneType topLessSceneType = mSceneStack.getTopLessSceneType();
        if(topSceneType == SceneType.TITLE) {

        } else if(topSceneType == SceneType.GAME) {
            //Game.getInstance().earthQuake();
        }
        mSceneStack.processTouchState(TouchState.getInstance());
        mSceneStack.step();
    }
}