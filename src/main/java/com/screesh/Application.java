package com.screesh;

import com.screesh.console.ScreeningsImport;
import com.screesh.console.SolutionPrinter;
import com.screesh.model.FilmFestival;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        String filepath = "src/test/resources/festival_input.json";
        FilmFestival ff = ScreeningsImport.buildFromJson(filepath);
        SolutionPrinter.printSolution(ff.getShows(), "SOLUZIONE 1");
    }
}
