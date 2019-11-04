package com.screesh.console;

import com.screesh.choosinghelper.ChoiceMaker;
import com.screesh.model.Movie;

import java.util.*;

public class ChooseWithTerminal implements ChoiceMaker {
    private static final List<String> POSITIVE_ANSWERS = Arrays.asList("y", "ye", "yes");
    private static final List<String> NEGATIVE_ANSWERS = Arrays.asList("n", "no");

    public boolean confirmExclusion(List<Movie> toBeExcluded) {
        System.out.println("---------- ESCLUDERE I SEGUENTI FILM? (yes/no) ----------");
        for (Movie m : toBeExcluded)
            System.out.println(m);

        return takeDecision();
    }

    public int chooseOneToExclude(List<Movie> toChoose) {
        System.out.println("---------- SCEGLI UN FILM DA ESCLUDERE ----------");

        int indexToExclude = -1;
        for (int i = 0; i < toChoose.size(); i++) {
            printChoice(i + 1, toChoose.get(i));
        }
        Scanner scan = new Scanner(System.in);
        try {
            indexToExclude = scan.nextInt() - 1;
        } catch(InputMismatchException ignored) { }

        if(indexToExclude < 0 || indexToExclude >= toChoose.size())
            indexToExclude = -1;

        return indexToExclude;
    }

    private boolean takeDecision() {
        String answer = null;
        Scanner scan = new Scanner(System.in);
        while(answer == null) {
            answer = scan.next();
            if(!isValidAnswer(answer)) {
                System.out.println("Rispondi yes/no");
                answer = null;
            }
        }
        return POSITIVE_ANSWERS.contains(answer.toLowerCase());
    }

    private static boolean isValidAnswer(String answer) {
        boolean ctrl = answer != null;
        if(ctrl) {
            String answerLowerCase = answer.toLowerCase();
            ctrl = POSITIVE_ANSWERS.contains(answerLowerCase) ||
                    NEGATIVE_ANSWERS.contains(answerLowerCase);
        }
        return ctrl;
    }

    private static void printChoice(int num, Movie movie) {
        System.out.println(num + " --> " + movie);
    }
}
