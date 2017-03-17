package cz.mxmx.a2048.controls;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by mxmx on 17.3.17.
 */

public class GameGestureDetector extends SwipeGestureDetector {
    public GameGestureDetector(Context ctx) {
        super(ctx);
    }

    @Override
    public void onSwipeRight() {
    }

    @Override
    public void onSwipeLeft() {
    }

    @Override
    public void onSwipeTop() {
    }

    @Override
    public void onSwipeBottom() {
    }
}
