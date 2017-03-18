package cz.mxmx.a2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cz.mxmx.a2048.game.Game;
import cz.mxmx.a2048.game.GameBoard;
import cz.mxmx.a2048.game.GameStateChangedListener;

import static cz.mxmx.a2048.R.string.score;

public class BoardActivity extends AppCompatActivity implements GameStateChangedListener {
    protected TextView scoreTextView;
    protected TextView highScoreTextView;
    private GameBoard board;
    private Game game;
    private int highScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        this.board = (GameBoard) this.findViewById(R.id.gameBoard);
        this.scoreTextView = (TextView) this.findViewById(R.id.currentScore);
        this.highScoreTextView = (TextView) this.findViewById(R.id.highScore);
        this.game = new Game(this.board.getGestureDetector(), this.board, this);
        this.game.start();
    }

    @Override
    public void gameStateChanged() {
        if (this.scoreTextView != null && this.highScoreTextView != null) {
            int score = this.game.getScore();
            this.scoreTextView.setText(score + "");

            if (score > this.highScore) {
                this.highScore = score;
                this.highScoreTextView.setText(this.highScore + "");
            }
        }
    }
}
