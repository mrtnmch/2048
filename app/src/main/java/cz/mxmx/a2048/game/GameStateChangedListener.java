package cz.mxmx.a2048.game;

/**
 * Game state change listener.
 */
public interface GameStateChangedListener {
    /**
     * Send "game state changed" message.
     */
    public void gameStateChanged();
}
