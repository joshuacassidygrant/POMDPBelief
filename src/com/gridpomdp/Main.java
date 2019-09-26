package com.gridpomdp;

public class Main {

    public static void main(String[] args) {

        qA();
        qB();
        qC();
        qD();
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
        System.out.println("qA:");
        Grid gridWorld = generateFreshGridWorld();
        gridWorld.setBeliefsUniform();
        gridWorld.command(Direction.UP, Evidence.TWO_WALL);
        gridWorld.command(Direction.UP, Evidence.TWO_WALL);
        gridWorld.command(Direction.UP, Evidence.TWO_WALL);
        gridWorld.printEvidenceModule();
    }

    public static void qB() {
        System.out.println("qB:");
        Grid gridWorld = generateFreshGridWorld();
        gridWorld.setBeliefsUniform();
        gridWorld.command(Direction.UP, Evidence.ONE_WALL);
        gridWorld.command(Direction.UP, Evidence.ONE_WALL);
        gridWorld.command(Direction.UP, Evidence.ONE_WALL);
        gridWorld.printEvidenceModule();
    }

    public static void qC() {
        System.out.println("qC:");
        Grid gridWorld = generateFreshGridWorld();
        gridWorld.setBeliefsStateCoord(2, 3);
        gridWorld.command(Direction.RIGHT, Evidence.ONE_WALL);
        gridWorld.command(Direction.RIGHT, Evidence.ONE_WALL);
        gridWorld.command(Direction.UP, Evidence.TERMINAL);
        gridWorld.printEvidenceModule();
    }

    public static void qD() {
        System.out.println("qD:");
        Grid gridWorld = generateFreshGridWorld();
        gridWorld.setBeliefsStateCoord(1, 1);
        gridWorld.command(Direction.UP, Evidence.TWO_WALL);
        gridWorld.command(Direction.RIGHT, Evidence.TWO_WALL);
        gridWorld.command(Direction.RIGHT, Evidence.ONE_WALL);
        gridWorld.command(Direction.RIGHT, Evidence.ONE_WALL);

        gridWorld.printEvidenceModule();
    }
}
