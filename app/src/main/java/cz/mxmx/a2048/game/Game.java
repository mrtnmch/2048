package cz.mxmx.a2048.game;

import java.util.Random;

/**
 * Created by mxmx on 18.3.17.
 */

public class Game implements SwipeGestureListener {
    private static final Integer TARGET_VALUE = 2048;
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

    protected void move(FieldsContainer newFields) {
        this.newField(newFields);
        this.board.setCurrentFields(newFields);
        this.board.invalidate();
        this.currentFields = newFields;
        this.triggerInfo();
    }

    protected void triggerInfo() {
        if (this.gameStateChangedListener != null) {
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
        } while (temp != null);

        copy.setField(top, left, 2 * (1 + this.random.nextInt(2)));
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

    public boolean isLoose() {
        return !this.canMove();
    }

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
