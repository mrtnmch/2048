package cz.mxmx.a2048.game;

import java.util.Random;

/**
 * Created by mxmx on 18.3.17.
 */

public class Game implements SwipeGestureListener {
    private final GameGestureDetector gameGestureDetector;
    private final GameStateChangedListener gameStateChangedListener;
    private final GameBoard board;
    private final Random random;
    private final int dimension;
    private int score;
    private FieldsContainer currentFields;

    public Game(GameGestureDetector gameGestureDetector, GameBoard board) {
        this(gameGestureDetector, board, null);
    }

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

        for (int i = 0; i < this.dimension; i++) {
            for (int j = this.dimension - 1; j >= 0; j--) {
                Integer val = copy.getField(i, j);

                if (val != null) {
                    Integer temp;

                    for (int k = j + 1; k < this.dimension; k++) {
                        temp = copy.getField(i, k);

                        if (temp != null) {
                            if (temp.equals(val)) {
                                copy.setField(i, k, val + temp);
                                copy.setField(i, k - 1, null);
                                this.addScore(val + temp);
                                moved = true;
                                break;
                            }
                        } else {
                            copy.setField(i, k, val);
                            copy.setField(i, k - 1, null);
                            moved = true;
                        }
                    }
                }
            }
        }

        if(moved) {
            this.move(copy);
        }
    }

    @Override
    public void onSwipeLeft() {
        FieldsContainer copy = new FieldsContainer(this.currentFields);
        boolean moved = false;

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 1; j < this.dimension; j++) {
                Integer val = copy.getField(i, j);

                if (val != null) {
                    Integer temp;

                    for (int k = j - 1; k >= 0; k--) {
                        temp = copy.getField(i, k);

                        if (temp != null) {
                            if (temp.equals(val)) {
                                copy.setField(i, k, val + temp);
                                copy.setField(i, k + 1, null);
                                this.addScore(val + temp);
                                moved = true;
                                break;
                            }
                        } else {
                            copy.setField(i, k, val);
                            copy.setField(i, k + 1, null);
                            moved = true;
                        }
                    }
                }
            }
        }

        if(moved) {
            this.move(copy);
        }
    }

    @Override
    public void onSwipeTop() {
        FieldsContainer copy = new FieldsContainer(this.currentFields);
        boolean moved = false;

        for (int i = 1; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                Integer val = copy.getField(i, j);

                if (val != null) {
                    Integer temp;

                    for (int k = i - 1; k >= 0; k--) {
                        temp = copy.getField(k, j);

                        if (temp != null) {
                            if (temp.equals(val)) {
                                copy.setField(k, j, val + temp);
                                copy.setField(k + 1, j, null);
                                this.addScore(val + temp);
                                moved = true;
                                break;
                            }
                        } else {
                            copy.setField(k, j, val);
                            copy.setField(k + 1, j, null);
                            moved = true;
                        }
                    }
                }
            }
        }

        if(moved) {
            this.move(copy);
        }
    }

    @Override
    public void onSwipeBottom() {
        FieldsContainer copy = new FieldsContainer(this.currentFields);
        boolean moved = false;

        for (int i = this.dimension - 1; i >= 0; i--) {
            for (int j = 0; j < this.dimension; j++) {
                Integer val = copy.getField(i, j);

                if (val != null) {
                    Integer temp;

                    for (int k = i + 1; k < this.dimension; k++) {
                        temp = copy.getField(k, j);

                        if (temp != null) {
                            if (temp.equals(val)) {
                                copy.setField(k, j, val + temp);
                                copy.setField(k - 1, j, null);
                                this.addScore(val + temp);
                                moved = true;
                                break;
                            }
                        } else {
                            copy.setField(k, j, val);
                            copy.setField(k - 1, j, null);
                            moved = true;
                        }
                    }
                }
            }
        }

        if(moved) {
            this.move(copy);
        }
    }

    protected void move(FieldsContainer newFields) {
        this.newField(newFields);
        this.board.setCurrentFields(newFields);
        this.board.invalidate();
        this.currentFields = newFields;
        this.triggerInfo();
    }

    protected void triggerInfo() {
        if(this.gameStateChangedListener != null) {
            this.gameStateChangedListener.gameStateChanged();
        }
    }

    private void newField(FieldsContainer copy) {
        Integer temp;
        int top, left;

        do {
            top = this.random.nextInt(this.dimension);
            left = this.random.nextInt(this.dimension);
            temp = copy.getField(top, left);
        } while(temp != null);

        copy.setField(top, left, 2);
    }

    public void start() {
        int left = this.random.nextInt(this.dimension);
        int top = this.random.nextInt(this.dimension);
        this.currentFields = new FieldsContainer(this.dimension);
        this.currentFields.setField(top, left, 2);
        this.board.setCurrentFields(this.currentFields);
        this.triggerInfo();
    }

    protected void addScore(int value) {
        this.score += value;
    }

    public int getScore() {
        return score;
    }
}
