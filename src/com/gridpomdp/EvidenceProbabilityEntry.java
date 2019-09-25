package com.gridpomdp;

public class EvidenceProbabilityEntry {
    public Belief Belief;
    public double Certainty;

    public EvidenceProbabilityEntry(Belief belief, double certainty) {
        Belief = belief;
        Certainty = certainty;
    }
}
