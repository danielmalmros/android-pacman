package org.example.pacman;

/**
 * This class should contain information about a single GoldCoin.
 * such as x and y coordinates (int) and whether or not the goldcoin
 * has been taken (boolean)
 */

public class GoldCoin {
    private int coinx, coiny;
    public boolean taken = false;

    public int getCoinx() {
        return coinx;
    }

    public int getCoiny() {
        return coiny;
    }

    public void setCoin(int coinx, int coiny) {
        this.coinx = coinx;
        this.coiny = coiny;
    }

    public GoldCoin(int coinx, int coiny) {
        this.coinx = coinx;
        this.coiny = coiny;
    }

}
