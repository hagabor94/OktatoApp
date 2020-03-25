package hu.hajasgabor.oktatoapp;

public class PupilForTeacher extends Pupil {
    String parent;

    public PupilForTeacher(String name, int solvedTests, float avgPoints, String parent) {
        super(name, solvedTests, avgPoints);
        this.parent=parent;
    }

    public String getParent(){return parent;};
}
