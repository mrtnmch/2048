package cz.mxmx.a2048.game;

import android.content.Context;

/**
 * Created by mxmx on 17.3.17.
 */

public interface GameGestureDetector {
    public void addSwipeGestureListener(SwipeGestureListener listener);
    public void removeSwipeGestureListener(SwipeGestureListener listener);
    public void clearSwipeGestureListeners();
}
