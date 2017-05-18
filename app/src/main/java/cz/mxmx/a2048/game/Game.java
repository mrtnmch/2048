package cz.mxmx.a2048.game;

import java.util.Random;

/**
 * 2048 Game representation.
 */
public class Game implements SwipeGestureListener {

    /** Target value to reach for */
    private static final Integer TARGET_VALUE = 2048;

    /** Game gesture detector (swiping) */
    private final GameGestureDetector gameGestureDetector;

    /**  Game state changed (fields moved) listener. */
    private final GameStateChangedListener gameStateChangedListener;

    /** Game board */
    private final GameBoard board;

    /** Random number generator */
    private final Random random;

    /** Dimensions of the game board */
    private final int dimension;

    /** Current score */
    private int score;

    /** Current game fields */
    private FieldsContainer currentFields;

    /**
     * Creates new game without any listener.
     * @param gameGestureDetector Gesture detector to use.
     * @param board Game board.
     */
    public Game(GameGestureDetector gameGestureDetector, GameBoard board) {
        this(gameGestureDetector, board, null);
    }

    /**
     * Creates new game with listener to send updates to.
     * @param gameGestureDetector Gesture detector to use.
     * @param board Game board.
     * @param listener Listener to send updates to.
     */
    public Game(GameGestureDetector gameGestureDetector, GameBoard board, GameStateChangedListener listener) {
        this.score = 0;
        this.gameGestureDetector = gameGestureDetector;
        gameStateChangedListener = listener;
        this.gameGestureDetector.addSwipeGestureListener(this);
        this.board = board;
        this.dimension = board.getBoardDimension();
        this.currentFields = new FieldsContainer(this.dimension);
        this.board.setCurrentFields(this.currentFields);
        this.board.invalidate();
        random = new Random();
    }

    @Override
    public void onSwipeRight() {
        FieldsContainer copy = new FieldsContainer(this.currentFields);
        boolean moved = false;

        for (int j = 0; j < this.dimension; j++) {
            for (int i = this.dimension - 1; i > 0; i--) {

                for (int k = i - 1; k >= 0; k--) {
                    Integer field = copy.getField(j, i);
                    Integer fieldCmp = copy.getField(j, k);

                    if (field == null && fieldCmp != null) {
                        copy.setField(j, i, fieldCmp);
                        copy.setField(j, k, null);
                        moved = true;
                        k++;
                        continue;
                    } else if (field != null && field.equals(fieldCmp)) {
                        copy.setField(j, i, field + fieldCmp);
                        copy.setField(j, k, null);
                        this.addScore(field + fieldCmp);
                        moved = true;
                        break;
                    } else if (fieldCmp != null && !field.equals(fieldCmp)) {
                        break;
                    } else if (fieldCmp == null) {
                        continue;
                    }
                }
            }
        }

        if (moved) {
            this.move(copy);
        }
    }

    @Override
    public void onSwipeLeft() {
        FieldsContainer copy = new FieldsContainer(this.currentFields);
        boolean moved = false;

        for (int j = 0; j < this.dimension; j++) {
            for (int i = 0; i < this.dimension; i++) {

                for (int k = i + 1; k < this.dimension; k++) {
                    Integer field = copy.getField(j, i);
                    Integer fieldCmp = copy.getField(j, k);

                    if (field == null && fieldCmp != null) {
                        copy.setField(j, i, fieldCmp);
                        copy.setField(j, k, null);
                        moved = true;
                        k--;
                        continue;
                    } else if (field != null && field.equals(fieldCmp)) {
                        copy.setField(j, i, field + fieldCmp);
                        copy.setField(j, k, null);
                        this.addScore(field + fieldCmp);
                        moved = true;
                        break;
                    } else if (fieldCmp != null && !field.equals(fieldCmp)) {
                        break;
                    } else if (fieldCmp == null) {
                        continue;
                    }
                }
            }
        }

        if (moved) {
            this.move(copy);
        }
    }

    @Override
    public void onSwipeTop() {
        FieldsContainer copy = new FieldsContainer(this.currentFields);
        boolean moved = false;

        for (int j = 0; j < this.dimension; j++) {
            for (int i = 0; i < this.dimension; i++) {

                for (int k = i + 1; k < this.dimension; k++) {
                    Integer field = copy.getField(i, j);
                    Integer fieldCmp = copy.getField(k, j);

                    if (field == null && fieldCmp != null) {
                        copy.setField(i, j, fieldCmp);
                        copy.setField(k, j, null);
                        moved = true;
                        k--;
                        continue;
                    } else if (field != null && field.equals(fieldCmp)) {
                        copy.setField(i, j, field + fieldCmp);
                        copy.setField(k, j, null);
                        this.addScore(field + fieldCmp);
                        moved = true;
                        break;
                    } else if (fieldCmp != null && !field.equals(fieldCmp)) {
                        break;
                    } else if (fieldCmp == null) {
                        continue;
                    }
                }
            }
        }

        if (moved) {
            this.move(copy);
        }
    }

    @Override
    public void onSwipeBottom() {
        FieldsContainer copy = new FieldsContainer(this.currentFields);
        boolean moved = false;

        for (int j = 0; j < this.dimension; j++) {
            for (int i = this.dimension - 1; i > 0; i--) {

                for (int k = i - 1; k >= 0; k--) {
                    Integer field = copy.getField(i, j);
                    Integer fieldCmp = copy.getField(k, j);

                    if (field == null && fieldCmp != null) {
                        copy.setField(i, j, fieldCmp);
                        copy.setField(k, j, null);
                        moved = true;
                        k++;
                        continue;
                    } else if (field != null && field.equals(fieldCmp)) {
                        copy.setField(i, j, field + fieldCmp);
                        copy.setField(k, j, null);
                        this.addScore(field + fieldCmp);
                        moved = true;
                        break;
                    } else if (fieldCmp != null && !field.equals(fieldCmp)) {
                        break;
                    } else if (fieldCmp == null) {
                        continue;
                    }
                }
            }
        }

        if (moved) {
            this.move(copy);
        }
    }

    /**
     * Sets new fields to the game board.
     * @param newFields New fields.
     */
    protected void move(FieldsContainer newFields) {
        this.newField(newFields);
        this.board.setCurrentFields(newFields);
        this.board.invalidate();
        this.currentFields = newFields;
        this.triggerInfo();
    }

    /**
     * Triggers game state changed message listener (if any).
     */
    protected void triggerInfo() {
        if (this.gameStateChangedListener != null) {
            this.gameStateChangedListener.gameStateChanged();
        }
    }

    /**
     * Add a new value to the fields container.
     * @param container Container to use.
     */
    private void newField(FieldsContainer container) {
        Integer temp;
        int top, left;

        do {
            top = this.random.nextInt(this.dimension);
            left = this.random.nextInt(this.dimension);
            temp = container.getField(top, left);
        } while (temp != null);

        container.setField(top, left, 2 * (1 + this.random.nextInt(2)));
    }

    /**
     * Start a game.
     */
    public void start() {
        int left = this.random.nextInt(this.dimension);
        int top = this.random.nextInt(this.dimension);
        this.currentFields = new FieldsContainer(this.dimension);
        this.currentFields.setField(top, left, 2);
        this.board.setCurrentFields(this.currentFields);
        this.triggerInfo();
    }

    /**
     * Add new value to the score.
     * @param value Score += value
     */
    protected void addScore(int value) {
        this.score += value;
    }

    public int getScore() {
        return score;
    }

    /**
     * @return Returns true if any of the fields has reached the targed value (ie 2048).
     */
    public boolean isWin() {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if(this.currentFields.getField(i, j) != null && this.currentFields.getField(i, j).equals(TARGET_VALUE)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return Returns true if there's no possible move.
     */
    public boolean isLoose() {
        return !this.canMove();
    }

    /**
     * @return Returns true if there's a possible move.
     */
    private boolean canMove() {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension - 1; j++) {
                Integer val1 = this.currentFields.getField(i, j);
                Integer val2 = this.currentFields.getField(i, j + 1);

                if(val1 == null || val2 == null || val1.equals(val2)) {
                    return true;
                }
            }
        }

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension - 1; j++) {
                Integer val1 = this.currentFields.getField(j, i);
                Integer val2 = this.currentFields.getField(j + 1, i);

                if(val1 == null || val2 == null || val1.equals(val2)) {
                    return true;
                }
            }
        }

        return false;
    }
}
