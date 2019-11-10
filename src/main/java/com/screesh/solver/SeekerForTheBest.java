package com.screesh.solver;

class SeekerForTheBest {
    private SolutionAnalyzer analyzer;

    public SeekerForTheBest(SolutionAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public boolean isWorthToAdd(PlacedOverTime pot) {
        return true;
    }
}
