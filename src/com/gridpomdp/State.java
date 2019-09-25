package com.gridpomdp;

import java.util.HashMap;
import java.util.List;

public class State {
    public int X;
    public int Y;
    public HashMap<Direction, State> connectedStates;
    public HashMap<Direction, List<TransitionProbabilityEntry>> transitionModel;
    public HashMap<Direction, List<EvidenceProbabilityEntry>> evidenceModel;

    public State(int x, int y) {
        X = x;
        Y = y;
        connectedStates = new HashMap<Direction, State>();
    }

    public State getConnectedStateOrSelf(Direction direction) {
        if (connectedStates.containsKey(direction)) return connectedStates.get(direction);
        return this;
    }

}
