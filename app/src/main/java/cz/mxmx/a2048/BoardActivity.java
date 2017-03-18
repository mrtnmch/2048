package cz.mxmx.a2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.mxmx.a2048.game.Game;
import cz.mxmx.a2048.game.GameBoard;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        GameBoard board = (GameBoard) this.findViewById(R.id.gameBoard);
        Game game = new Game(board.getGestureDetector(), board);
        game.start();
    }
}
