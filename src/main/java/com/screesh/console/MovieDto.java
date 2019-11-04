package com.screesh.console;

import java.util.List;

public class MovieDto {
    private String title;
    private int year;
    private int runtime;
    private int priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    private List<ScreeningDto> screenings;

    public List<ScreeningDto> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<ScreeningDto> screenings) {
        this.screenings = screenings;
    }
}
