package com.wx.multihero;



import android.app.Activity;
import android.os.Bundle;

import com.wx.multihero.variability.Game;

public class MainActivity extends Activity {
    private MainView mMainView;
    
    private static String ICICLE_KEY = "multihero";

    /**
     * Called when Activity is first created. Turns off the title bar, sets up
     * the content views, and fires up the SnakeView.
     * 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        mMainView = (MainView)findViewById(R.id.mainView);

        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mMainView.setGameState(Game.State.READY);
        } else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mMainView.restoreState(map);
            } else {
                mMainView.setGameState(Game.State.PAUSED);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mMainView.setGameState(Game.State.PAUSED);
    }

    @Override
    public void onBackPressed() {
        if(mMainView != null) {
            mMainView.back();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
        outState.putBundle(ICICLE_KEY, mMainView.saveState());
    }
}
