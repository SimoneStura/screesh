package com.screesh.model;

import com.screesh.TestCommonData;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.*;

class FilmFestivalTest {
    private FilmFestival ff;
    
    @BeforeEach
    void setUp() {
        ff = new FilmFestival("film festival name", 0);
    }
    
    @Test
    void setCinemas() {
        //given
        Collection<Cinema> cinemas = ff.getCinemas();
        Assert.assertNotNull(cinemas);
        int previousSize = cinemas.size();
        Cinema c1 = new Cinema("Cinema1");
        Cinema c2 = new Cinema("Cinema2");
    
        //when
        ff.addCinema(c1);
        ff.addCinema(c2);
        ff.setDistanceBetweenCinemas(c1.getName(), c2.getName(), 10);
    
        //then
        Assert.assertNotNull(cinemas);
        Assert.assertThat(cinemas, hasSize(previousSize + 2));
        Assert.assertTrue(cinemas.contains(c1));
        Assert.assertTrue(cinemas.contains(c2));
        Assert.assertEquals(10, c1.getDistance(c2));
    }
    
    @Test
    void addMovie() {
        //given
        Set<Movie> movies = ff.getMovies();
        Assert.assertNotNull(movies);
        Assert.assertThat(movies, hasSize(0));
        Movie m = TestCommonData.createMovie("Movie");
        
        //when
        ff.addMovie(m);
        movies = ff.getMovies();
        
        //then
        Assert.assertNotNull(movies);
        Assert.assertThat(movies, hasSize(1));
        Assert.assertThat(movies, contains(m));
    }
    
    @Test
    void addScreening_singleScreening() {
        //given
        Movie m = TestCommonData.createMovie("Movie");
        ff.addMovie(m);
        SortedSet<Screening> shows = ff.getShows();
        List<Screening> screens = ff.getScreens(m);
        Assert.assertNotNull(shows);
        Assert.assertThat(shows, hasSize(0));
        Assert.assertNotNull(screens);
        Assert.assertThat(screens, hasSize(0));
        Screening s = new Screening(m, LocalDateTime.of(2015, 11, 1, 22, 0), new Cinema("cinema1"), "1");
    
        //when
        ff.addScreening(s);
        shows = ff.getShows();
        screens = ff.getScreens(m);
    
        //then
        Assert.assertNotNull(shows);
        Assert.assertThat(shows, hasSize(1));
        Assert.assertThat(shows, contains(s));
        Assert.assertNotNull(screens);
        Assert.assertThat(screens, hasSize(1));
        Assert.assertThat(screens, contains(s));
    }
    
    @Test
    void addScreening_screeningList() {
        //given
        Movie m = TestCommonData.createMovie("Movie");
        ff.addMovie(m);
        SortedSet<Screening> shows = ff.getShows();
        List<Screening> screens = ff.getScreens(m);
        Assert.assertNotNull(shows);
        Assert.assertThat(shows, hasSize(0));
        Assert.assertNotNull(screens);
        Assert.assertThat(screens, hasSize(0));
        ArrayList<Screening> screenings = new ArrayList<>();
        Screening s1 = new Screening(m, LocalDateTime.of(2015, 11, 1, 22, 0), new Cinema("cinema1"), "1");
        screenings.add(s1);
        Screening s2 = new Screening(m, LocalDateTime.of(2015, 11, 1, 21, 0), new Cinema("cinema2"), "3");
        screenings.add(s2);
    
        //when
        ff.addScreenings(screenings);
        shows = ff.getShows();
        screens = ff.getScreens(m);
    
        //then
        Assert.assertNotNull(shows);
        Assert.assertThat(shows, hasSize(2));
        Assert.assertThat(shows, contains(screenings.get(1), screenings.get(0))); // properly sorted
        Assert.assertNotNull(screens);
        Assert.assertThat(screens, hasSize(2));
        Assert.assertThat(screens, containsInAnyOrder(screenings.get(0), screenings.get(1)));
    }
}