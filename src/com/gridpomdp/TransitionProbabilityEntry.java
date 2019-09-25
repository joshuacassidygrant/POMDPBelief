package com.gridpomdp;

public class TransitionProbabilityEntry {
    public State State;
    public double Probability;

    public TransitionProbabilityEntry(State state, double probability) {
        State = state;
        Probability = probability;
    }
}
