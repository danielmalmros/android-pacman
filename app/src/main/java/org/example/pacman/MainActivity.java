package org.example.pacman;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.button;
import static android.R.attr.direction;

public class MainActivity extends Activity {
    //reference to the main view
    GameView gameView;
    //reference to the game class.
    Game game;

    //The time used for the pacman
    private Timer levelTimer;

    private Timer myTimer;
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //saying we want the game to run in one mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        gameView =  findViewById(R.id.gameView);
        TextView textView = findViewById(R.id.points);
        TextView timerView = findViewById(R.id.levelTimer);

        game = new Game(this, textView, timerView);
        game.setGameView(gameView);
        gameView.setGame(game);

        game.newGame();

        //level time
        levelTimer = new Timer();
        levelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                LevelTimerMethod();
            }
        }, 0, 1000);

        //make a new timer
        myTimer = new Timer();
        //We will call the timer 5 times each second
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 16);

        Button buttonRight = findViewById(R.id.moveRight);
        //listener of our pacman, when somebody clicks it
        buttonRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.direction = 2;
                game.running = true;
                //game.movePacmanRight(10);
            }
        });

        Button buttonLeft = findViewById(R.id.moveLeft);
        //listener of our pacman, when somebody clicks it
        buttonLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.direction = 4;
                game.running = true;
                //game.movePacmanLeft(10);
            }
        });

        Button buttonUp = findViewById(R.id.moveUp);
        //listener of our pacman, when somebody clicks it
        buttonUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.direction = 1;
                game.running = true;
                //game.movePacmanUp(10);
            }
        });

        Button buttonDown = findViewById(R.id.moveDown);
        //listener of our pacman, when somebody clicks it
        buttonDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.direction = 3;
                game.running = true;
                //game.movePacmanDown(10);
            }
        });
    }

    private void LevelTimerMethod()
    {
        this.runOnUiThread(levelTimer_Tick);
    }

    private Runnable levelTimer_Tick = new Runnable() {
        public void run() {
            if (game.running) {
                game.levelTime--;
                game.setTimerText();

            }
            if (game.gameOver) {
                reset();
            }
        }
    };

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.
            // so we can draw
            if (game.running )
            {
                counter++;
                //update the counter - notice this is NOT seconds in this example
                //you need TWO counters - one for the time and one for the pacman
                //textView.setText("Timer value: "+counter);
                if (game.direction == 1) {
                    game.movePacmanUp(4);
                }

                if (game.direction == 2) {
                    game.movePacmanRight(4);
                }

                if (game.direction == 3) {
                    game.movePacmanDown(4);
                }

                if (game.direction == 4) {
                    game.movePacmanLeft(4);
                }
                 //move the pacman - you
                //should call a method on your game class to move
                //the pacman instead of this
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this,"settings clicked",Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_newGame) {
            Toast.makeText(this,"New Game clicked",Toast.LENGTH_LONG).show();
            reset();
            return true;
        }

        if (id == R.id.action_pauseGame) {
            game.direction = 0;
            game.running = false;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void reset() {
        recreate();
        levelTimer.cancel();
        myTimer.cancel();
    }
}
