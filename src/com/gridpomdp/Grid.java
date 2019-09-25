package com.gridpomdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid
{
    private State[][] states;
    int XWidth;
    int YHeight;
    HashMap<Evidence, List<EvidenceProbabilityEntry>> observationModel;
    public int numberOfNonTerminalStates;
    HashMap<State, Double> beliefs;

    /**
     * Constructs a grid of the specified width and height
     * @param xWidth
     * @param yHeight
     */
    public Grid(int xWidth, int yHeight) {
        XWidth = xWidth;
        YHeight = yHeight;
        states = new State[xWidth][yHeight];

    }

    /**
     * Adds a state at x, y on the grid
     * @param x, position on x axis(the width of the grid)
     * @param y, position on y
     * @param reward
     * @param terminal, whether or not this is a final state
     */
    public void addState(int x, int y, double reward, boolean terminal) {
        if (x < 0 || x >= XWidth || y < 0 || y >= YHeight) {
            throw new IndexOutOfBoundsException();
        }

        State state = new State(x, y);
        states[x][y] = state;
        state.terminal = terminal;
        state.reward = reward;
    }

    /**
     * Initializes the grid, adding a specific transition model to every state based on its position,
     * and creates the grid's observation model
     */
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

                state.walls = 4 - state.connectedStates.size();

                state.transitionModel = new HashMap<Direction, List<TransitionProbabilityEntry>>();

                //Build the transition model for state at x, y
                for (int i = 0; i <= 3; i++) {

                    List<TransitionProbabilityEntry> entries = new ArrayList<TransitionProbabilityEntry>();
                    TransitionProbabilityEntry e1 = new TransitionProbabilityEntry(state.getConnectedStateOrSelf(Direction.fromInt(i)), 0.8);
                    TransitionProbabilityEntry e2 = new TransitionProbabilityEntry(state.getConnectedStateOrSelf(Direction.fromInt(i + 1)), 0.1);
                    TransitionProbabilityEntry e3 = new TransitionProbabilityEntry(state.getConnectedStateOrSelf(Direction.fromInt(i - 1)), 0.1);

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


                    entries.add(e1);
                    if (e2.Probability > 0) entries.add(e2);
                    if (e3.Probability > 0) entries.add(e3);

                    state.transitionModel.put(Direction.fromInt(i), entries);
                }

            }
        }

        observationModel = new HashMap<Evidence, List<EvidenceProbabilityEntry>>();

        EvidenceProbabilityEntry e1 = new EvidenceProbabilityEntry(Belief.NON_TERMINAL_IN_THIRD_COLUMN, 0.9);
        EvidenceProbabilityEntry e2 = new EvidenceProbabilityEntry(Belief.ALL_OTHER_NON_TERMINAL, 0.1);
        List<EvidenceProbabilityEntry> owList = new ArrayList<EvidenceProbabilityEntry>();
        owList.add(e1);
        owList.add(e2);
        observationModel.put(Evidence.ONE_WALL, owList);

        EvidenceProbabilityEntry e3 = new EvidenceProbabilityEntry(Belief.NON_TERMINAL_IN_THIRD_COLUMN, 0.1);
        EvidenceProbabilityEntry e4 = new EvidenceProbabilityEntry(Belief.ALL_OTHER_NON_TERMINAL, 0.9);
        List<EvidenceProbabilityEntry> twList = new ArrayList<EvidenceProbabilityEntry>();
        twList.add(e3);
        twList.add(e4);
        observationModel.put(Evidence.TWO_WALL, twList);

        EvidenceProbabilityEntry e5 = new EvidenceProbabilityEntry(Belief.TERMINAL, 1);
        List<EvidenceProbabilityEntry> tList = new ArrayList<EvidenceProbabilityEntry>();
        tList.add(e5);
        observationModel.put(Evidence.TERMINAL, tList);

        System.out.println("Initialization complete.");
    }

    public void setBeliefsUniform(){
        double value = 1.0 / (double)numberOfNonTerminalStates;
        HashMap<State, Double> newBeliefs = new HashMap<State, Double>();
        for (State[] col : states) {
            for (State state : col) {
                if (state != null) {
                    if (state.terminal) {
                        newBeliefs.put(state, 0.0);
                    } else {
                        newBeliefs.put(state, value);
                    }
                }
            }
        }
        beliefs = newBeliefs;
        printEvidenceModule();
    }

    public void setBeliefsStateCoord(int x, int y) {

    }

    public void command(Direction action, Evidence evidence) {
        //Updates the belief state based on action and evidence

        HashMap<State, Double> newBeliefs = new HashMap<State, Double>();
        for (Map.Entry<State, Double> entry : beliefs.entrySet()) {
            State thisState = entry.getKey();

            double observationCertainty = 0;

            for (EvidenceProbabilityEntry epe : observationModel.get(evidence)) {
                if (epe.Belief == thisState.getTrueBelief()) {
                    observationCertainty = epe.Certainty;
                }
            }

            double certainty = 0;
            //We will calculate our certainty that we are in each state in the belief set by multiplying the chance that
            //we were in each previous set by the chance we will transition to that set based on our action:
            List<State> connectedStates = new ArrayList<State>(thisState.connectedStates.values()); //Empty. Fix this.
            for (State conState : connectedStates ) {
                double chance = conState.getChanceToGoTo(thisState, action);
                double certaintyOfBeingInConState = beliefs.get(conState);
                certainty += chance * certaintyOfBeingInConState;
            }
            newBeliefs.put(thisState, certainty * observationCertainty);
        }
        beliefs = newBeliefs;

    }

    public void printEvidenceModule(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<State, Double> entry : beliefs.entrySet()){
            sb.append(entry.getKey().coordsString());
            sb.append(": ");
            sb.append(entry.getValue().toString());
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * Returns true if there is a state in the grid at the specified x, y coordinates.
     * @param x
     * @param y
     * @return
     */
    private boolean stateAt(int x, int y) {
        return (x >= 0 && y >= 0 && x < XWidth && y < YHeight && states[x][y] != null);
    }
}


