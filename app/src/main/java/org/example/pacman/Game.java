package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * This class should contain all your game logic
 */

public class Game {
    //context is a reference to the activity
    private Context context;

    public boolean gameOver = false;

    private int points = 0; //how points do we have
    public int levelTime = 60; // set time value for game

    //bitmap of the pacman
    private Bitmap pacBitmap;

    //textview reference to points
    private TextView pointsView;

    //textview reference to timer
    private TextView timerView;


    private int pacx, pacy;
    //the list of goldcoins - initially empty
    private ArrayList<GoldCoin> coins = new ArrayList<>();

    //the list of enemy - initially empty
    private ArrayList<Enemy> enemies = new ArrayList<>();

    //you should put in the running in the game class
    public boolean running = false;
    public int direction = 0;

    //a reference to the gameview
    private GameView gameView;
    private int h,w; //height and width of screen
    private boolean coinsInitialized = false;
    private boolean enemyInitialized = false;

    // check if gold coins is initialized
    public boolean getCoinsInitialized()
    {
        return coinsInitialized;
    }

    // check if gold coins is initialized
    public boolean getEnemyInitialized()
    {
        return enemyInitialized;
    }

    public Game(Context context, TextView view, TextView timerView) {
        this.context = context;
        this.pointsView = view;
        this.timerView = timerView;
        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);
    }

    // Init gold coins
    public void initCoins()
    {
        //init coins here - with w and h.
        for (int i = 0; i < 10; i++) {

            Random r = new Random();

            int x = r.nextInt(w);
            int y = r.nextInt(h);

            coins.add(new GoldCoin(x,y));
        }
        coinsInitialized = true;
    }

    // Init gold coins
    public void initEnemy()
    {
        for (int i = 0; i < 1; i++) {

            Random r = new Random();

            int x = r.nextInt(w);
            int y = r.nextInt(h);

            enemies.add(new Enemy(x,y));
        }
        enemyInitialized = true;
    }

    public void setGameView(GameView view)
    {
        this.gameView = view;
    }

    //TODO initialize goldcoins also here
    public void newGame()
    {
        pacx = 50;
        pacy = 400; //just some starting coordinates
        //reset the points
        points = 0;
        setPointsText();
        setTimerText();
        gameView.invalidate(); //redraw screen
    }

    public void setPointsText() {
        pointsView.setText(context.getResources().getString(R.string.points)+" "+points);

        if (points == 10) {
            Toast toast = Toast.makeText(context.getApplicationContext(), "YOU WON BITCH!", Toast.LENGTH_LONG);
            toast.show();
            running = false;
        }
    }

    public void setTimerText() {
        timerView.setText(context.getResources().getString(R.string.levelTimer)+" "+levelTime);

        if (levelTime == 0) {
            direction = 0;
            running = false;
            gameOver = true;
            Toast toast = Toast.makeText(context.getApplicationContext(), "END OF GAME!", Toast.LENGTH_LONG);
            toast.show();
        }
}


    public void setSize(int h, int w)
    {
        this.h = h;
        this.w = w;
    }

    public void movePacmanRight(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacx+pixels+pacBitmap.getWidth()<w) {
            pacx = pacx + pixels;
            gameView.invalidate();
        }
    }

    public void movePacmanLeft(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacx-pixels+pacBitmap.getWidth() < w && pacx > 0) {
            pacx = pacx - pixels;
            gameView.invalidate();
        }
    }

    public void movePacmanUp(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacy-pixels+pacBitmap.getHeight() < h && pacy > 0) {
            pacy = pacy - pixels;
            gameView.invalidate();
        }
    }

    public void movePacmanDown(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacy+pixels+pacBitmap.getHeight() < h) {
            pacy = pacy + pixels;
            gameView.invalidate();
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    public void doCollisionCheck()
    {
        int radius = 40;
        int middlePacX = pacx + getPacBitmap().getWidth() / 2;
        int middlePacY = pacy + getPacBitmap().getHeight() / 2;

        for (GoldCoin coin: getCoins()) {
            int a = Math.abs(middlePacX - coin.getCoinx());
            int b = Math.abs(middlePacY - coin.getCoiny());

            double collision = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

            if(collision < radius && !coin.taken) {
                System.out.println("HIT");
                coin.taken = true;

                points++;
                setPointsText();

                // Incress enemy speed when pacman takes points
                for(Enemy enemy : getEnemies()) {
                    if (getPoints() == 3) {
                        enemy.getSpeed(2);
                    } else if (getPoints() == 6) {
                        enemy.getSpeed(3);
                    }
                }

                gameView.invalidate();
            }
        }

        // Check if enemy touch pacman
        for(Enemy enemy : getEnemies()) {
            enemy.attackPacMan(middlePacX, middlePacY);
            gameView.invalidate();
            int a = Math.abs(middlePacX - enemy.getEnemyX());
            int b = Math.abs(middlePacY - enemy.getEnemyY());
            if (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)) < radius) {
                running = false;
                gameOver = true;
                Toast toast = Toast.makeText(context.getApplicationContext(), "Du tabte", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    public int getPacx()
    {
        return pacx;
    }

    public int getPacy()
    {
        return pacy;
    }

    public int getPoints()
    {
        return points;
    }

    public ArrayList<GoldCoin> getCoins()
    {
        return coins;
    }
    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }

    public Bitmap getPacBitmap()
    {
        return pacBitmap;
    }


}
