package com.wx.flappybird;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final int FPS = 90;
    private static final float GRAVITY = 0.35f;
    private static final float FLY_VELOCITY = 4.0f;
    private static final int CEILING_TOP = 64;
    private static final int BIRD_OFFSET_X = 45;
    private static final int MAP_SCROLL_PIXELS_PER_FRAME = 2;
    private static final int PIPE_GAP_MIN_HEIGHT = 60;
    private static final int PIPE_GAP_MAX_HEIGHT = 100;
    private static final Random RND = new Random();

    private int mFrameCounter;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mLimitTop;
    private int mLimitBottom;
    private GameState mGameState;
    private int mScore;
    private int mHighScore;
    private float mVelocity;
    private float mGravity;
    private boolean mFlyOver;
    private boolean mAutoPilot = true;

    private int mMapOffsetX;
    private ArrayList<Bitmap> mSmallNumbers = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> mBigNumbers = new ArrayList<Bitmap>();
    private Bitmap mLandBitmap;
    private int mLandCount;
    private Bitmap mSkyBitmap;
    private int mSkyCount;
    private Bitmap mWallBitmap;
    private int mWallCount;
    private Bitmap mSplashBitmap;
    private Bitmap mScoreBoardBitmap;
    private Sprite mReplayButton = new Sprite();
    private ArrayList<Bitmap> mMedalBitmapList = new ArrayList<Bitmap>();

    private Bird mBird = new Bird();
    private Bitmap mPipeBodyBitmap;
    private Bitmap mPipeDownBitmap;
    private Bitmap mPipeUpBitmap;
    private ArrayList<Pipe> mPipeList = new ArrayList<MainView.Pipe>();
    private final Paint mPaint = new Paint();
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private SoundPool mSoundPool;
    private int mSoundDie = -1;
    private int mSoundHit = -1;
    private int mSoundWing = -1;
    private int mSoundSwooshing = -1;
    private int mSoundPoint = -1;
    private int mFps;

    private class Sprite {
        Rect rect = new Rect();
        Bitmap bitmap;
    }

    private class Pipe {
        public RectF up = new RectF();
        public RectF down = new RectF();
        public int gap;
        public Bitmap bodyBitmap;
        public Bitmap upBitmap;
        public Bitmap downBitmap;

        public void draw(Canvas canvas, Paint paint) {
            // up pipe
            drawPipeBody(canvas, up.left, up.top,
                    up.bottom - up.top - downBitmap.getHeight(), paint);
            canvas.drawBitmap(downBitmap, up.left,
                    up.bottom - downBitmap.getHeight(), paint);

            // down pipe
            drawPipeBody(canvas, down.left, down.top + upBitmap.getHeight(),
                    down.bottom - down.top - upBitmap.getHeight(), paint);
            canvas.drawBitmap(upBitmap, down.left, down.top, paint);
        }

        private void drawPipeBody(Canvas mCanvas, float x, float y, float height,
                                  Paint paint) {
            android.graphics.Rect src = new android.graphics.Rect();
            android.graphics.RectF dest = new android.graphics.RectF();
            src.left = 0;
            src.top = 0;
            src.right = bodyBitmap.getWidth();
            src.bottom = bodyBitmap.getHeight();
            dest.left = x;
            dest.top = y;
            dest.right = x + bodyBitmap.getWidth();
            dest.bottom = y + height;
            mCanvas.drawBitmap(bodyBitmap, src, dest, paint);
            for (int i = 0; i < height; i++) {
                //mCanvas.drawBitmap(bodyBitmap, x, y, paint);
                y += 1;
            }
        }

    }

    private class Bird {
        public RectF rect = new RectF();
        private int bmpIndex;
        private ArrayList<Bitmap> bmpList = new ArrayList<Bitmap>();

        public Bird() {
            bmpIndex = 0;
        }

        public void switchNextFrame() {
            bmpIndex += 1;
            if (bmpIndex >= bmpList.size())
                bmpIndex = 0;
        }

        public Bitmap getCurrentBitmap() {
            return bmpList.get(bmpIndex);
        }
    }

    private Bitmap loadBitmap(String path) {
        AssetManager am = this.getContext().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = am.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    private boolean collided(RectF r1, RectF r2) {
        RectF r = new RectF();
        r.left = r1.left > r2.left ? r1.left : r2.left;
        r.right = r1.right < r2.right ? r1.right : r2.right;
        r.top = r1.top > r2.top ? r1.top : r2.top;
        r.bottom = r1.bottom < r2.bottom ? r1.bottom : r2.bottom;
        return (r.right > r.left) && (r.bottom > r.top);
    }

    private void playSound(int id) {
        mSoundPool.play(id, 1.0f, 1.0f, 100, 0, 1f);
    }

    private void loadAssets() {
        mSplashBitmap = loadBitmap("pictures/splash.png");
        mScoreBoardBitmap = loadBitmap("pictures/scoreboard.png");
        mSkyBitmap = loadBitmap("pictures/sky.png");
        mWallBitmap = loadBitmap("pictures/wall.png");
        mLandBitmap = loadBitmap("pictures/land.png");
        mReplayButton.bitmap = loadBitmap("pictures/replay.png");
        mPipeDownBitmap = loadBitmap("pictures/pipe_down.png");
        mPipeUpBitmap = loadBitmap("pictures/pipe_up.png");
        mPipeBodyBitmap = loadBitmap("pictures/pipe.png");

        for (int i = 0; i < 10; i++) {
            mSmallNumbers.add(loadBitmap(String.format("pictures/font_small_%d.png", i)));
        }
        for (int i = 0; i < 10; i++) {
            mBigNumbers.add(loadBitmap(String.format("pictures/font_big_%d.png", i)));
        }
        for (int i = 0; i < 4; i++) {
            mBird.bmpList.add(loadBitmap(String.format("pictures/bird_%d.png", i)));
        }
        for (int i = 0; i < 4; i++) {
            mMedalBitmapList.add(loadBitmap(String.format("pictures/medal_%d.png", i)));
        }

        // sound resources
        Context context = getContext();
        mSoundPool = new SoundPool(400, AudioManager.STREAM_MUSIC, 100);
        mSoundDie = mSoundPool.load(context, R.raw.sfx_die, 1);
        mSoundHit = mSoundPool.load(context, R.raw.sfx_hit, 1);
        mSoundWing = mSoundPool.load(context, R.raw.sfx_wing, 1);
        mSoundSwooshing = mSoundPool.load(context, R.raw.sfx_swooshing, 1);
        mSoundPoint = mSoundPool.load(context, R.raw.sfx_point, 1);
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

    private void init() {
        mSurfaceHolder = getHolder();//�õ�SurfaceHolder����
        mSurfaceHolder.addCallback(this);//ע��SurfaceHolder
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);//������Ļ����
    }

    void jump() {
        mVelocity = -FLY_VELOCITY;
        //playSound(mSoundWing);
    }

    float getMaxJumpHeight(float velocity, float gravity) {
        float y = 0;
        while (velocity < 0) {
            y += velocity;
            velocity += gravity;
        }
        return -y;
    }

    private RectF simulate(RectF rect, float flySpeed, float velocity, float gravity, int frames) {
        float h = rect.bottom - rect.top;
        float w = rect.right - rect.left;

        while (frames > 0) {
            velocity += gravity;
            rect.left += flySpeed;
            rect.top += velocity;
            frames -= 1;
        }
        // rect.left += 0.5;
        // rect.top += 0.5;
        rect.right = rect.left + w;
        rect.bottom = rect.top + h;
        return rect;
    }

    private void autopilot() {
        if (!mAutoPilot)
            return;

        RectF fakeBirdRect = new RectF(mBird.rect);
        int index = 0;
        // find the next pipe
        int i;
        for (i = 0; i < mPipeList.size(); i++) {
            if (mPipeList.get(i).down.right >= fakeBirdRect.left) {
                index = i;
                break;
            }
        }
        Pipe currentPipe = mPipeList.get(index);
        Pipe nextPipe = mPipeList.get(index + 1);
        boolean needJump = false;
        boolean nextPipeIsUp = nextPipe.up.bottom < currentPipe.up.bottom;
        RectF nextRect = simulate(fakeBirdRect, MAP_SCROLL_PIXELS_PER_FRAME, mVelocity, mGravity, 1);
        float maxJumpHeight = getMaxJumpHeight(-FLY_VELOCITY, mGravity) + 5;
        if (nextPipeIsUp) {
            if (fakeBirdRect.top > currentPipe.up.bottom + maxJumpHeight) {
                needJump = true;
            }
        } else {
            if (nextRect.bottom >= currentPipe.down.top) {
                needJump = true;
            }
        }
        if (nextRect.bottom >= mLimitBottom) {
            needJump = true;
        }
        if (needJump) {
            jump();
        }
    }

    private void generateRandomPipe() {
        int validHeight = mLimitBottom - mLimitTop;
        int randGapHeight = RND.nextInt(PIPE_GAP_MAX_HEIGHT - PIPE_GAP_MIN_HEIGHT) + PIPE_GAP_MIN_HEIGHT;
        int maxUpHeight = validHeight - randGapHeight - mPipeDownBitmap.getHeight() - mPipeUpBitmap.getHeight() - mPipeBodyBitmap.getHeight() * 2;
        int upHeight = RND.nextInt(maxUpHeight) + mPipeDownBitmap.getHeight() + mPipeBodyBitmap.getHeight();
        float downHeight = validHeight - upHeight - randGapHeight;
        float startX = 0;
        Pipe newPipe = new Pipe();
        int pipeSize = mPipeList.size();
        if (pipeSize > 0) {
            float heightDiff = Math.abs(downHeight - (mPipeList.get(pipeSize - 1).down.bottom - mPipeList.get(pipeSize - 1).down.top));
            newPipe.gap = (int) (randGapHeight > heightDiff ? randGapHeight : heightDiff);
            startX = mPipeList.get(pipeSize - 1).down.right + newPipe.gap;
        } else {
            newPipe.gap = PIPE_GAP_MAX_HEIGHT;
            startX = mScreenWidth / 2;
        }
        newPipe.downBitmap = mPipeDownBitmap;
        newPipe.upBitmap = mPipeUpBitmap;
        newPipe.bodyBitmap = mPipeBodyBitmap;
        newPipe.up.left = startX;
        newPipe.up.top = mLimitTop;
        newPipe.up.right = startX + mPipeBodyBitmap.getWidth();
        newPipe.up.bottom = mLimitTop + upHeight;

        newPipe.down.left = startX;
        newPipe.down.top = mLimitBottom - downHeight;
        newPipe.down.right = startX + mPipeBodyBitmap.getWidth();
        newPipe.down.bottom = mLimitBottom;

        mPipeList.add(newPipe);
    }

    private void updatePipes() {
        int i;
        int unitWidth = mPipeBodyBitmap.getWidth();
        int needPipes = mScreenWidth / unitWidth - mPipeList.size();
        for (i = 0; i < needPipes; i++) {
            generateRandomPipe();
        }

        if (mGameState == GameState.RUNNING) {
            // update pipe's position
            for (Pipe pipe : mPipeList) {
                pipe.up.left -= MAP_SCROLL_PIXELS_PER_FRAME;
                pipe.up.right -= MAP_SCROLL_PIXELS_PER_FRAME;
                pipe.down.left -= MAP_SCROLL_PIXELS_PER_FRAME;
                pipe.down.right -= MAP_SCROLL_PIXELS_PER_FRAME;
                if (collided(mBird.rect, pipe.up) || collided(mBird.rect, pipe.down)) {
                    playSound(mSoundHit);
                    gameOver();
                }
            }
            if (mPipeList.get(0).up.right >= 0) {
                if (!mFlyOver && mPipeList.get(0).up.right < mBird.rect.left) {
                    mScore++;
                    mFlyOver = true;
                    playSound(mSoundPoint);
                }
            } else {
                mPipeList.remove(0);
                generateRandomPipe();
                mFlyOver = false;
            }
        }
    }

    private void gameOver() {
        mGameState = GameState.GAMEOVER;
        playSound(mSoundDie);
        if (mScore > mHighScore) {
            mHighScore = mScore;
        }
    }

    public void update() {
        if (mGameState == GameState.RUNNING || mGameState == GameState.GAMEOVER) {
            mVelocity += mGravity;
            mBird.rect.top += mVelocity;
            mBird.rect.right = mBird.rect.left + mBird.getCurrentBitmap().getWidth();
            mBird.rect.bottom = mBird.rect.top + mBird.getCurrentBitmap().getHeight();
        }

        if (mBird.rect.top + mBird.getCurrentBitmap().getHeight() > mLimitBottom) {
            mBird.rect.top = mLimitBottom - mBird.getCurrentBitmap().getHeight();
        }
        if (mFrameCounter % 3 == 0 && mGameState != GameState.GAMEOVER) {
            mBird.switchNextFrame();
        }
        if (mGameState == GameState.RUNNING) {
            mMapOffsetX += MAP_SCROLL_PIXELS_PER_FRAME;
            // ���Ƹ߶�
            if (mBird.rect.top < mLimitTop)
                mBird.rect.top = mLimitTop;
            else if (mBird.rect.top + mBird.getCurrentBitmap().getHeight() >= mLimitBottom) {
                playSound(mSoundSwooshing);
                gameOver();
            }
        }
        updatePipes();

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
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        mReplayButton.rect.left = (mScreenWidth - mReplayButton.bitmap.getWidth()) / 2;
        mReplayButton.rect.top = (mScreenHeight - mScoreBoardBitmap.getHeight()) / 2 + 200;
        mReplayButton.rect.right = mReplayButton.rect.left + mReplayButton.bitmap.getWidth();
        mReplayButton.rect.bottom = mReplayButton.rect.top + mReplayButton.bitmap.getHeight();

        mLandCount = mScreenWidth / mLandBitmap.getWidth() + 2;
        mSkyCount = mScreenWidth / mSkyBitmap.getWidth() + 2;
        mWallCount = mScreenWidth / mWallBitmap.getWidth() + 2;
        mLimitBottom = mScreenHeight - mLandBitmap.getHeight();
        mLimitTop = CEILING_TOP + mWallBitmap.getHeight();

        mBird.rect.top = (mScreenHeight - mBird.getCurrentBitmap().getHeight()) / 2;
        mBird.rect.left = BIRD_OFFSET_X;

        mPipeList.clear();
    }

    public GameState getGameState() {
        return mGameState;
    }

    public void setGameState(GameState gameState) {
        mGameState = gameState;
    }

    public Bundle saveState() {
        Bundle map = new Bundle();

        // map.putIntArray("mAppleList", coordArrayListToArray(mAppleList));
        // map.putInt("mDirection", mDirection.ordinal());
        // map.putInt("mNextDirection", mNextDirection.ordinal());
        // map.putLong("mMoveDelay", Long.valueOf(mMoveInterval));
        // map.putLong("mScore", Long.valueOf(mScore));
        // map.putIntArray("mSnakeTrail", coordArrayListToArray(mSnakeTrail));

        return map;
    }

    public void restoreState(Bundle icicle) {
        setGameState(GameState.PAUSED);

        // mAppleList = coordArrayToArrayList(icicle.getIntArray("mAppleList"));
        // mDirection = Dir.values()[icicle.getInt("mDirection")];
        // mNextDirection = Dir.values()[icicle.getInt("mNextDirection")];
        // mMoveInterval = icicle.getLong("mMoveDelay");
        // mScore = icicle.getLong("mScore");
        // mSnakeTrail =
        // coordArrayToArrayList(icicle.getIntArray("mSnakeTrail"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mScreenWidth = w;
        mScreenHeight = h;
    }

    private void drawRecycleBitmaps(Canvas mCanvas, Bitmap bitmap, int count, int x, int y) {
        int bitmapWidth = bitmap.getWidth();
        int startX = x % bitmapWidth;
        for (int i = 0; i < count; i++) {
            mCanvas.drawBitmap(bitmap, i * bitmapWidth - startX, y, mPaint);
        }
    }

    private void drawNumbers(Canvas mCanvas, ArrayList<Bitmap> mBitmaps, int x, int y, int number) {
        if (mBitmaps.size() < 1)
            return;

        int w = mBitmaps.get(0).getWidth();
        do {
            mCanvas.drawBitmap(mBitmaps.get(number % 10), x, y, mPaint);
            number /= 10;
            x -= w + 2;
        } while (number > 0);
    }

    private void drawing() {

        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if (mCanvas == null)
                return;

            mCanvas.drawColor(0xFF4EC0CA);

            // land
            drawRecycleBitmaps(mCanvas, mLandBitmap, mLandCount, mMapOffsetX, mLimitBottom);

            // sky
            drawRecycleBitmaps(mCanvas, mSkyBitmap, mSkyCount, mMapOffsetX/2, mLimitBottom - mSkyBitmap.getHeight());

            // wall
            drawRecycleBitmaps(mCanvas, mWallBitmap, mWallCount, mMapOffsetX,mLimitTop - mWallBitmap.getHeight());

            // big score
            drawNumbers(mCanvas, mBigNumbers, 10 + (mBigNumbers.get(0).getWidth() + 5) * 5, CEILING_TOP - mBigNumbers.get(0).getHeight() - 5, mScore);

            // pipes
            for (Pipe pipe : mPipeList) {
                pipe.draw(mCanvas, mPaint);
            }

            // bird
            mCanvas.drawBitmap(mBird.getCurrentBitmap(), mBird.rect.left, mBird.rect.top, mPaint);

            // splash
            int x = 0, y = 0;
            Bitmap bmp = null;
            if (mGameState == GameState.READY) {
                bmp = mSplashBitmap;
                x = (int) (mBird.rect.left - mMapOffsetX);
                y = (mScreenHeight - mSplashBitmap.getHeight()) / 2;
                ;
            } else if (mGameState == GameState.GAMEOVER) {
                x = (mScreenWidth - mScoreBoardBitmap.getWidth()) / 2;
                y = (mScreenHeight - mScoreBoardBitmap.getHeight()) / 2;
                bmp = mScoreBoardBitmap;
            }
            if (bmp != null) {
                mCanvas.drawBitmap(bmp, x, y, mPaint);
            }
            if (mGameState == GameState.GAMEOVER) {
                drawNumbers(mCanvas, mSmallNumbers, x + 212, y + 109, mScore);
                drawNumbers(mCanvas, mSmallNumbers, x + 212, y + 151, mHighScore);

                // draw medal
                int level = mScore / 10;
                if (level > mMedalBitmapList.size()) {
                    level = mMedalBitmapList.size() - 1;
                }

                mCanvas.drawBitmap(mMedalBitmapList.get(level), x + 32, y + 113, mPaint);

                // Restart button
                mCanvas.drawBitmap(mReplayButton.bitmap, mReplayButton.rect.left, mReplayButton.rect.top, mPaint);
            }
            mCanvas.drawText(String.format("%d", mFps), mScreenWidth - 100, mLimitTop - 40, mPaint);
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
        if (mGameState == GameState.READY) {
            mGameState = GameState.RUNNING;
        } else if (mGameState == GameState.RUNNING) {
            jump();
            if (mAutoPilot) {
                mAutoPilot = false;
            }
        } else if (mGameState == GameState.GAMEOVER) {
            float x = event.getX();
            float y = event.getY();
            if (x >= mReplayButton.rect.left && x <= mReplayButton.rect.right
                    && y >= mReplayButton.rect.top && y <= mReplayButton.rect.bottom) {
                initNewGame();
            }
        }
        return super.onTouchEvent(event);
    }

    public void run() {
        try {
            long time = System.currentTimeMillis();
            int execCount = 0;
            while (true) {
                update();
                drawing();
                mFrameCounter += 1;
                Thread.sleep(1);
                execCount++;
                long diff = System.currentTimeMillis() - time;
                mFps = (int) ((long) execCount * 1000 / diff);
                //Log.i("fps", String.format("%f", mFps));
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
