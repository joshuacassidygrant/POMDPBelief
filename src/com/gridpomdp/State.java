package com.gridpomdp;

import java.util.HashMap;
import java.util.List;

public class State {
    public int X;
    public int Y;
    public HashMap<Direction, State> connectedStates;
    public HashMap<Direction, List<TransitionProbabilityEntry>> transitionModel;
    public int walls;
    public boolean terminal;
    public double reward;

    public State(int x, int y) {
        X = x;
        Y = y;
        connectedStates = new HashMap<Direction, State>();
    }

    public Evidence getEvidence() {
        if (terminal) return Evidence.TERMINAL;
        if (walls == 2) return Evidence.TWO_WALL;
        if (walls == 1) return Evidence.ONE_WALL;
        return Evidence.UNDEFINED;
    }

    public State getConnectedStateOrSelf(Direction direction) {
        if (connectedStates.containsKey(direction)) return connectedStates.get(direction);
        return this;
    }

    public String coordsString() {
        return "(" + X + "," + Y + ")";
    }
}
