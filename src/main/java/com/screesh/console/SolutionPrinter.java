package com.screesh.console;

import com.screesh.model.Screening;
import org.junit.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalUnit;
import java.util.Locale;
import java.util.SortedSet;

public class SolutionPrinter {
    private static DateTimeFormatter scheduleFormat = DateTimeFormatter.ofPattern("HH:mm");
    
    public static void printSolution(SortedSet<Screening> solution, String solutionTitle) {
        Assert.assertNotNull(solution);
        printTitle(solutionTitle);
    
        LocalDate lastDate = LocalDate.MIN;
        LocalDateTime lastEnding = null;
        for(Screening s : solution) {
            LocalDate currentDate = LocalDate.from(s.getStartTime());
            if(!currentDate.equals(lastDate))
                printDay(currentDate);
    
            printWaiting(lastEnding, s.getStartTime());
            printScreening(s);
            
            lastEnding = s.getEndTime();
            lastDate = currentDate;
        }
    }
    
    private static void printWaiting(LocalDateTime from, LocalDateTime to) {
        if(from != null) {
            String toPrint  = "\t";
            if(from.isBefore(to)) {
                Duration waitingTime = Duration.between(from, to);
                
                long minutes = waitingTime.toMinutes() % 60;
                long hours = waitingTime.toHours();
                
                if(hours > 0)
                    toPrint += hours + "h ";
                toPrint += minutes + "m";
            } else {
                toPrint += "####";
            }
            
            System.out.println(toPrint);
        }
    }
    
    private static void printTitle(String solutionTitle) {
        if(solutionTitle != null)
            System.out.println(solutionTitle);
    }
    
    private static void printDay(LocalDate currentDate) {
        Locale lang = Locale.ITALIAN;
        String toPrint = "\n" +
                currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, lang) +
                " " +
                currentDate.getDayOfMonth() +
                " " +
                currentDate.getMonth().getDisplayName(TextStyle.FULL, lang);
        System.out.println(toPrint);
    }
    
    private static void printScreening(Screening s) {
        String toPrint = s.getStartTime().format(scheduleFormat) +
                " - " +
                s.getEndTime().format(scheduleFormat) +
                " " +
                s.getScreened() +
                " | " +
                s.getCinema() +
                " " +
                s.getTheater();
        System.out.println(toPrint);
    }
}
