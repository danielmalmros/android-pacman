package org.example.pacman;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View {

	Game game;
    int h,w; //used for storing our height and width of the view

	public void setGame(Game game)
	{
		this.game = game;
	}


	/* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
	public GameView(Context context) {
		super(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}


	public GameView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
	}

	//In the onDraw we put all our code that should be
	//drawn whenever we update the screen.
	@Override
	protected void onDraw(Canvas canvas) {
		//Here we get the height and weight
		h = canvas.getHeight();
		w = canvas.getWidth();
		//update the size for the canvas to the game.
		game.setSize(h,w);
		//Making a new paint object

		Paint paint = new Paint();
		canvas.drawColor(Color.BLACK); //clear entire canvas to black color

		if (!game.getCoinsInitialized() && !game.getEnemyInitialized())
		{
			game.initCoins();
			game.initEnemy();
		}


		//loop through the list of goldcoins and draw them.
		for (GoldCoin coin: game.getCoins())
		{
		    if (!coin.taken) {
                paint.setColor(Color.YELLOW);
                canvas.drawCircle(coin.getCoinx(), coin.getCoiny(), 20, paint);
            }
        }

		//loop through the list of enemy and draw them.
		for (Enemy enemy: game.getEnemies())
		{
				paint.setColor(Color.RED);
				canvas.drawCircle(enemy.getEnemyX(), enemy.getEnemyY(), 70, paint);
		}

		//draw the pacman
		canvas.drawBitmap(game.getPacBitmap(), game.getPacx(),game.getPacy(), paint);

		super.onDraw(canvas);
	}
}
