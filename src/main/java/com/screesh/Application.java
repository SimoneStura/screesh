package com.screesh;

import com.screesh.console.ScreeningsImport;
import com.screesh.console.SolutionPrinter;
import com.screesh.model.FilmFestival;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

public class Application {
    public static void main(String[] args) throws IOException {
        String filepath = "src/test/resources/festival_input.json";
        FilmFestival ff = ScreeningsImport.buildFromJson(filepath);
        SolutionPrinter.printSolution(ff.getShows(), "SOLUZIONE 1");
        
        Duration d = Duration.ofMinutes(5);
        Duration d1 = d.minusMinutes(4);
        Duration d2 = d.minusMinutes(5);
        Duration d3 = d.minusMinutes(6);
        System.out.println("d -> " + d.toMinutes());
        System.out.println("d1 -> " + d1.toMinutes());
        System.out.println("d2 -> " + d2.toMinutes());
        System.out.println("d3 -> " + d3.toMinutes());
    }
}
