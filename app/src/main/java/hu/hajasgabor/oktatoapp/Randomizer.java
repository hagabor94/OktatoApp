package hu.hajasgabor.oktatoapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Randomizer {
    public Randomizer(){}

    public List<Integer> RandomQuizAnswers() {
        List<Integer> temp = Arrays.asList(2,3,4,5);
        Collections.shuffle(temp);
        return temp;
    }

    public List<Integer> RandomQuizQuestions(int questions) {
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < questions; i++){
            temp.add(i);
        }
        Collections.shuffle(temp);
        return temp;
    }



}
