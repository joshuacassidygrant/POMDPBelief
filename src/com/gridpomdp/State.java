package com.gridpomdp;

import java.util.ArrayList;
import java.util.List;

public class State {
    public int X;
    public int Y;
    public List<State> connectedStates;

    public State(int x, int y) {
        X = x;
        Y = y;
        connectedStates = new ArrayList<State>();
    }


    //TODO: Add transition model
    //TODO: Add sensor model
}
