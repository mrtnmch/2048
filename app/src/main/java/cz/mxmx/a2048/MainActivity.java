package cz.mxmx.a2048;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Main activity (entry point).
 */
public class MainActivity extends AppCompatActivity {

    /** Text view with current high score */
    private TextView highScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.highScoreTextView = (TextView) this.findViewById(R.id.mainHighScore);
        this.highScoreTextView.setText(this.getHighScore() + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.highScoreTextView.setText(this.getHighScore() + "");
    }

    /**
     * Start a new activity to start the game
     * @param caller Caller.
     */
    protected void startPlaying(View caller) {
        Intent startPlating = new Intent(this, BoardActivity.class);
        this.startActivity(startPlating);
    }

    protected int getHighScore() {
        SharedPreferences preferences = this.getSharedPreferences(this.getString(R.string.preference_key), Context.MODE_PRIVATE);
        return preferences.getInt(this.getString(R.string.high_score_key), 0);
    }

    protected void resetHighScore() {
        SharedPreferences preferences = this.getSharedPreferences(this.getString(R.string.preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(this.getString(R.string.high_score_key), 0);
        editor.apply();
    }
}

