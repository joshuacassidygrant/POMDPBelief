package com.gridpomdp;

public class Grid
{
    private State[][] states;
    int XWidth;
    int YHeight;

    public Grid(int xWidth, int yHeight) {
        XWidth = xWidth;
        YHeight = yHeight;
        states = new State[xWidth][yHeight];

    }

    public void addState(int x, int y, double reward, boolean absorbing) {
        if (x < 0 || x >= XWidth || y < 0 || y >= YHeight) {
            throw new IndexOutOfBoundsException();
        }

        State state = new State(x, y);
        states[x][y] = state;

    }

    public void initialize() {
        // Iterates over each item in the array to create its transition and belief tables
        for (int x = 0; x < XWidth; x++) {
            for (int y = 0; y < YHeight; y++) {
                State state = states[x][y];

            }
        }
    }
}


