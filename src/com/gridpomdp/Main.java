package com.gridpomdp;

public class Main {

    public static void main(String[] args) {


        Grid gridWorld = new Grid(4, 3);
        gridWorld.addState(0, 0, -0.04f, false);
        gridWorld.addState(0, 1, -0.04f, false);
        gridWorld.addState(0, 2, -0.04f, false);
        gridWorld.addState(1, 0, -0.04f, false);
        // no state 1, 1
        gridWorld.addState(1, 2, -0.04f, false);
        gridWorld.addState(2, 0, -0.04f, false);
        gridWorld.addState(2, 1, -0.04f, false);
        gridWorld.addState(2, 2, -0.04f, false);
        gridWorld.addState(3, 0, -0.04f, false);
        gridWorld.addState(3,1 , -1f, false);
        gridWorld.addState(3,2, 1f, true);

        gridWorld.initialize();
    }
}
