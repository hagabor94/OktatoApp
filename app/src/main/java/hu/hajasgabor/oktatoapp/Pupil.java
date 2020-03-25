package hu.hajasgabor.oktatoapp;

public class Pupil {
    private String name;
    private int solvedTests;
    private float avgPoints;

    public Pupil(String name, int solvedTests, float avgPoints) {
        this.name = name;
        this.solvedTests = solvedTests;
        this.avgPoints = avgPoints;
    }

    public String getName() {
        return name;
    }

    public int getSolvedTests() {
        return solvedTests;
    }

    public float getAvgPoints() {
        return avgPoints;
    }
}
