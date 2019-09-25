package com.gridpomdp;

public class Main {

    public static void main(String[] args) {

        qA();

    }

    public static Grid generateFreshGridWorld() {
        Grid gridWorld = new Grid(4, 3);
        gridWorld.addState(0, 0, -0.04f, false);
        gridWorld.addState(0, 1, -0.04f, false);
        gridWorld.addState(0, 2, -0.04f, false);
        gridWorld.addState(1, 0, -0.04f, false);
        // state 1, 1 omitted
        gridWorld.addState(1, 2, -0.04f, false);
        gridWorld.addState(2, 0, -0.04f, false);
        gridWorld.addState(2, 1, -0.04f, false);
        gridWorld.addState(2, 2, -0.04f, false);
        gridWorld.addState(3, 0, -0.04f, false);
        gridWorld.addState(3,1 , -1f, true);
        gridWorld.addState(3,2, 1f, true);
        gridWorld.numberOfNonTerminalStates = 9;
        gridWorld.initialize();
        return gridWorld;
    }

    public static void qA() {
        Grid gridWorld = generateFreshGridWorld();
        gridWorld.setBeliefsUniform();
        gridWorld.command(Direction.UP, Evidence.TWO_WALL);
        gridWorld.command(Direction.UP, Evidence.TWO_WALL);
        gridWorld.command(Direction.UP, Evidence.TWO_WALL);

        gridWorld.printEvidenceModule();
    }
}
