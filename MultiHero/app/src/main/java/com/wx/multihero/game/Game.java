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
import com.wx.multihero.game.entity.CharacterManager;
import com.wx.multihero.game.entity.Map;
import com.wx.multihero.game.entity.ModManager;
import com.wx.multihero.game.ui.scene.BaseScene;
import com.wx.multihero.game.ui.scene.CharacterChooseScene;
import com.wx.multihero.game.ui.scene.GameScene;
import com.wx.multihero.game.ui.scene.LoadingScene;
import com.wx.multihero.game.ui.scene.MapChooseScene;
import com.wx.multihero.game.ui.scene.TitleScene;
import com.wx.multihero.game.variability.ui.Player;
import com.wx.multihero.os.TouchState;

import java.util.ArrayList;

public class Game implements BaseScene.Notify, Renderable, Stepable {
    private static final int FPS = 60;
    private int mTargetFps;
    private int mCurrentFps;
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
        mTargetFps = FPS;
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
            canvas.drawText(String.format("FPS:%2d", mCurrentFps),
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
            if(Utils.DEBUG) {
                ArrayList<Player> players = new ArrayList<Player>();
                Player player = new Player();
                player.setTeam(Player.Team.BLUE);
                player.setType(Player.Type.CPU);
                player.setCharacter(CharacterManager.getInstance().getCharacter(0));
                players.add(player);

                Map defaultMap = ModManager.getInstance().getMap(0);
                mGameScene.setMap(defaultMap, players);
                mSceneStack.clearPush(mGameScene);
            } else {
                mSceneStack.clearPush(mTitleScene);
            }
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

    public int getTargetFps() {
        return mTargetFps;
    }

    public void setTargetFps(int fps) {
        mTargetFps = fps;
    }

    public int getCurrentFps() {
        return mCurrentFps;
    }

    public void setCurrentFps(int currentFps) {
        mCurrentFps = currentFps;
    }
}
