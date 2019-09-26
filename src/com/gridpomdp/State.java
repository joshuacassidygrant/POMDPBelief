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

    public Belief getTrueBelief() {
        if (terminal) return Belief.TERMINAL;
        if (X == 2 && !terminal) return Belief.NON_TERMINAL_IN_THIRD_COLUMN;
        return Belief.ALL_OTHER_NON_TERMINAL;
    }

    public State getConnectedStateOrSelf(Direction direction) {
        if (connectedStates.containsKey(direction)) return connectedStates.get(direction);
        return this;
    }

    public double getChanceToGoTo(State state, Direction action) {

        for (TransitionProbabilityEntry e : transitionModel.get(action)) {
            if (e != null && e.State == state) return e.Probability;
        }
        return 0;
    }

    public String coordsString() {
        return "(" + (X + 1) + "," + (Y + 1) + ")";
    }
}
