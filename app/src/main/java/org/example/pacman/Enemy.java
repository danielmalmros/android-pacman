package org.example.pacman;

/**
 * Created by Daniel Malmros on 08-03-2018.
 */

public class Enemy {
    private int enemyX, enemyY;
    private int speed = 1;

    public int getEnemyX() {
        return enemyX;
    }

    public int getEnemyY() {
        return enemyY;
    }

    public void getSpeed(int newSpeed) {
        if (this.speed <= 3) {
            this.speed = newSpeed;
        }
    }

    public void setEnemy(int enemyX, int enemyY) {
        this.enemyX = enemyX;
        this.enemyY = enemyY;
    }

    public void attackPacMan(int pacX, int pacY) {
        if (this.enemyX > pacX) {
            this.enemyX-= speed;
        }
        if (this.enemyX < pacX) {
            this.enemyX+= speed;
        }
        if (this.enemyY > pacY) {
            this.enemyY-= speed;
        }
        if (this.enemyY < pacY) {
            this.enemyY+= speed;
        }
    }

    public Enemy(int enemyX, int enemyY) {
        this.enemyX = enemyX;
        this.enemyY = enemyY;
    }

}
