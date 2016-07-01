package com.pride.dungeon;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.pride.dungeon.managers.ResourceManager;
import com.pride.dungeon.model.ModelHolder;
import com.pride.dungeon.model.ModelLoader;
import com.pride.dungeon.view.GameView;

import java.io.IOException;

public class GameActivity extends AppCompatActivity {

    static GameView gameView;
    static ModelHolder modelHolder;
    GestureDetectorCompat mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        gameView = (GameView) findViewById(R.id.gameView);
        mGestureDetector = new GestureDetectorCompat(this, new MyGestureListener());

        loadModel();
        runMainLoop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    private void runMainLoop() {
        GameLoopThread gameLoop = new GameLoopThread(gameView);
        gameLoop.start();
    }

    private void loadModel() {
        ResourceManager.init(getAssets());
        try {
            modelHolder = ModelLoader.loadModel(
                    getAssets().open("resources.json"),
                    getAssets().open("1.lvl"        ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                                float distanceY) {
            //Log.d("GameActivity","onScroll1: " + event1.toString());
            //Log.d("GameActivity","onScroll2: " + event2.toString());

        //    modelHolder.player.x += distanceX;
        //    modelHolder.player.y += distanceY;
            return true;
        }
        @Override
        public boolean onDown(MotionEvent event) {
            float dxIndex = (((event.getX() - gameView.getWidth() / 2 )) / 64);
            float dyIndex = (((event.getY() - gameView.getHeight() / 2)) / 64);
            float xTo = (float) (Math.round(dxIndex) * 64 + modelHolder.player.x);
            float yTo = (float) (Math.round(dyIndex) * 64 + modelHolder.player.y);

            modelHolder.player.moveTo(xTo, yTo);
            return true;
        }
    }

}
