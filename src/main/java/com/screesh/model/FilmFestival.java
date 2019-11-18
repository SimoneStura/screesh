package com.screesh.model;

import java.util.*;

public class FilmFestival {
    private String name;
    private int minimumToWaitInMinutes;
    private Map<String, Cinema> cinemas;
    private HashSet<Movie> movies;
    private SortedSet<Screening> shows;
    private SortedSet<Integer> priorities;
    private Map<Movie, ArrayList<Screening>> screens;
    
    public FilmFestival(String name, int minimumToWaitInMinutes) {
        this.name = name;
        this.minimumToWaitInMinutes = minimumToWaitInMinutes;
        cinemas = new HashMap<>();
        movies = new HashSet<>();
        shows = new TreeSet<>();
        priorities = new TreeSet<>();
        screens = new HashMap<>();
    }
    
    public String getName() {
        return name;
    }
    
    public int getMinimumToWaitInMinutes() {
        return minimumToWaitInMinutes;
    }
    
    public Collection<Cinema> getCinemas() {
        return cinemas.values();
    }
    
    public SortedSet<Integer> getPriorities() {
        return priorities;
    }
    
    public HashSet<Movie> getMovies() {
        return movies;
    }
    
    public SortedSet<Screening> getShows() {
        return shows;
    }
    
    public List<Screening> getScreens(Movie showed) {
        return screens.get(showed);
    }
    
    public void addCinema(Cinema toAdd) {
        cinemas.put(toAdd.getName(), toAdd);
    }
    
    public void setDistanceBetweenCinemas(String cinema1, String cinema2, int distanceInMinutes) {
        Cinema c1 = cinemas.get(cinema1);
        Cinema c2 = cinemas.get(cinema2);
        c1.setDistance(c2, distanceInMinutes);
        c2.setDistance(c1, distanceInMinutes);
    }
    
    public boolean addMovie(Movie toAdd) {
        if(toAdd == null)
            throw new IllegalArgumentException();
        
        boolean ctrl = movies.add(toAdd);
        if(ctrl)
            screens.put(toAdd, new ArrayList<>());
        
        return ctrl;
    }
    
    public boolean addScreening(Screening toAdd) {
        if(toAdd == null)
            throw new IllegalArgumentException();
        
        ArrayList<Screening> listToAdd = new ArrayList<>();
        listToAdd.add(toAdd);
        
        return addScreenings(listToAdd);
    }
    
    public boolean addScreenings(List<Screening> toAdd) {
        if(toAdd == null || toAdd.size() == 0)
            throw new IllegalArgumentException();
        
        boolean ctrl = shows.addAll(toAdd);
        if(ctrl) {
            for(Screening singleScreening : toAdd) {
                ArrayList<Screening> screeningsOfMovie = screens.get(singleScreening.getScreened());
                screeningsOfMovie.add(singleScreening);
                priorities.add(singleScreening.getPriority());
            }
        }
        return ctrl;
    }
}
