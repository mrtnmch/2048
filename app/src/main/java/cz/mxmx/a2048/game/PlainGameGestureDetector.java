package cz.mxmx.a2048.game;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mxmx on 18.3.17.
 */

public class PlainGameGestureDetector extends SwipeGestureDetector implements GameGestureDetector {
    protected List<SwipeGestureListener> listeners;

    public PlainGameGestureDetector(Context ctx) {
        super(ctx);
        this.listeners = new ArrayList<>();
    }

    @Override
    protected void onSwipeRight() {
        for (SwipeGestureListener listener : this.listeners) {
            listener.onSwipeRight();
        }
    }

    @Override
    protected void onSwipeLeft() {
        for (SwipeGestureListener listener : this.listeners) {
            listener.onSwipeLeft();
        }
    }

    @Override
    protected void onSwipeTop() {
        for (SwipeGestureListener listener : this.listeners) {
            listener.onSwipeTop();
        }
    }

    @Override
    protected void onSwipeBottom() {
        for (SwipeGestureListener listener : this.listeners) {
            listener.onSwipeBottom();
        }
    }

    @Override
    public void addSwipeGestureListener(SwipeGestureListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeSwipeGestureListener(SwipeGestureListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void clearSwipeGestureListeners() {
        this.listeners.clear();
    }
}
