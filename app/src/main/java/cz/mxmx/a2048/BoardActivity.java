package cz.mxmx.a2048;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import cz.mxmx.a2048.game.Game;
import cz.mxmx.a2048.game.GameBoard;
import cz.mxmx.a2048.game.GameStateChangedListener;

import static cz.mxmx.a2048.R.string.score;

public class BoardActivity extends AppCompatActivity implements GameStateChangedListener {
    protected TextView scoreTextView;
    protected TextView highScoreTextView;
    private GameBoard board;
    private Game game;
    private int highScore;
    private int score;
    private boolean wonInformed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        this.board = (GameBoard) this.findViewById(R.id.gameBoard);
        this.scoreTextView = (TextView) this.findViewById(R.id.currentScore);
        this.highScoreTextView = (TextView) this.findViewById(R.id.highScore);
        this.wonInformed = false;
        this.restart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.confirmLeaveDialog();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    protected int getHighScore() {
        SharedPreferences preferences = this.getSharedPreferences(this.getString(R.string.preference_key), Context.MODE_PRIVATE);
        return preferences.getInt(this.getString(R.string.high_score_key), 0);
    }

    protected void saveHighScore(int score) {
        SharedPreferences preferences = this.getSharedPreferences(this.getString(R.string.preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(this.getString(R.string.high_score_key), score);
        editor.apply();
    }

    protected void restart() {
        this.game = new Game(this.board.getGestureDetector(), this.board, this);
        this.game.start();
        this.highScore = this.getHighScore();
        this.highScoreTextView.setText(this.highScore + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_restart:
                this.confirmRestartDialog();
                break;
            default:
                this.confirmLeaveDialog();
                break;
        }

        return true;
    }

    protected void confirmLeaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Leave the game");
        builder.setMessage("Do you really want to leave this game?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                checkSaveHighScore();
                finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void confirmRestartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Restart the game");
        builder.setMessage("Do you really want to restart this game?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                checkSaveHighScore();
                restart();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void checkSaveHighScore() {
        if (this.score > this.highScore) {
            this.highScore = this.score;
            this.highScoreTextView.setText(this.highScore + "");
            this.saveHighScore(this.score);
        }
    }

    @Override
    public void gameStateChanged() {
        if (this.scoreTextView != null && this.highScoreTextView != null) {
            this.score = this.game.getScore();
            this.scoreTextView.setText(this.score + "");
            this.checkSaveHighScore();

            if(this.game.isWin() && !this.wonInformed) {
                this.confirmWinDialog();
            }

            if(this.game.isLoose()) {
                this.confirmLostDialog();
            }
        }
    }

    private void confirmWinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("You won!");
        builder.setMessage("Congratulations, you won! Do you want to continue?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                checkSaveHighScore();
                finish();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void confirmLostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Looser!");
        builder.setMessage("You lost. :( Do you want to try again?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                checkSaveHighScore();
                restart();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkSaveHighScore();
                finish();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
