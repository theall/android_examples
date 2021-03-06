package com.wx.flappybird;


import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private MainView mMainView;

    private static String ICICLE_KEY = "flappy_bird";

    /**
     * Called when Activity is first created. Turns off the title bar, sets up
     * the content views, and fires up the SnakeView.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mMainView = (MainView) findViewById(R.id.mainView);
        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mMainView.setGameState(GameState.READY);
        } else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mMainView.restoreState(map);
            } else {
                mMainView.setGameState(GameState.PAUSED);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mMainView.setGameState(GameState.PAUSED);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
        outState.putBundle(ICICLE_KEY, mMainView.saveState());
    }

}
