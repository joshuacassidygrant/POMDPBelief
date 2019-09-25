package com.gridpomdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                if (state == null) break;

                if (stateAt(x-1, y)) {
                    state.connectedStates.put(Direction.LEFT, states[x-1][y]);
                }

                if (stateAt(x+1, y)) {
                    state.connectedStates.put(Direction.RIGHT, states[x+1][y]);
                }

                if(stateAt(x, y-1)) {
                    state.connectedStates.put(Direction.DOWN, states[x][y-1]);
                }

                if(stateAt(x, y+1)) {
                    state.connectedStates.put(Direction.UP, states[x][y+1]);
                }

                int walls = 4 - state.connectedStates.size();

                state.transitionModel = new HashMap<Direction, List<TransitionProbabilityEntry>>();
                List<TransitionProbabilityEntry> upEntries = new ArrayList<TransitionProbabilityEntry>();
                TransitionProbabilityEntry e1 = new TransitionProbabilityEntry(state.getConnectedStateOrSelf(Direction.UP), 0.8);
                TransitionProbabilityEntry e2 = new TransitionProbabilityEntry(state.getConnectedStateOrSelf(Direction.LEFT), 0.1);
                TransitionProbabilityEntry e3 = new TransitionProbabilityEntry(state.getConnectedStateOrSelf(Direction.RIGHT), 0.1);

                if (e1.State == e2.State) {
                    e1.Probability = e1.Probability + e2.Probability;
                    e2.Probability = 0;
                }

                if (e1.State == e3.State) {
                    e1.Probability = e1.Probability + e3.Probability;
                    e3.Probability = 0;
                }

                if (e3.State == e2.State) {
                    e3.Probability = e3.Probability + e2.Probability;
                    e2.Probability = 0;
                }

                upEntries.add(e1);
                upEntries.add(e2);
                upEntries.add(e3);

                state.transitionModel.put(Direction.UP, upEntries);

            }
        }
    }

    private boolean stateAt(int x, int y) {
        return (x >= 0 && y >= 0 && x < XWidth && y < YHeight && states[x][y] != null);
    }
}


