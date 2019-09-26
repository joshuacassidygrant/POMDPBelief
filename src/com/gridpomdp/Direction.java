package com.gridpomdp;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public static Direction fromInt(int i) {
        if (i < 0) i = 4 + i;
        i = i % 4;
        switch(i) {
            case 0: return UP;
            case 1: return RIGHT;
            case 2: return DOWN;
            case 3: return LEFT;
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
