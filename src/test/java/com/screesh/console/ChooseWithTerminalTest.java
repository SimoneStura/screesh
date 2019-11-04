package com.screesh.console;

import com.screesh.TestCommonData;
import com.screesh.TestUtils;
import com.screesh.model.Movie;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

class ChooseWithTerminalTest {
    
    private ChooseWithTerminal choose;

    @BeforeEach
    void setUp() {
        choose = new ChooseWithTerminal();
    }

    @Test
    void chooseOneToExclude_correctInput() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(2);
        TestUtils.fakeUserInput(String.valueOf(1));

        //when
        int result = choose.chooseOneToExclude(movies);

        //then
        Assert.assertEquals(0, result);
    }

    @Test
    void chooseOneToExclude_wrongInputType() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(2);
        TestUtils.fakeUserInput("stringValue");

        //when
        int result = choose.chooseOneToExclude(movies);

        //then
        Assert.assertEquals(-1, result);
    }

    @Test
    void chooseOneToExclude_wrongInput() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(2);
        TestUtils.fakeUserInput(String.valueOf(3));

        //when
        int result = choose.chooseOneToExclude(movies);

        //then
        Assert.assertEquals(-1, result);
    }

    @Test
    void confirmExclusion_correctPositiveInput() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(1);
        TestUtils.fakeUserInput("yes");

        //when
        boolean result = choose.confirmExclusion(movies);

        //then
        Assert.assertTrue(result);
    }

    @Test
    void confirmExclusion_correctNegativeInput() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(3);
        TestUtils.fakeUserInput("no");

        //when
        boolean result = choose.confirmExclusion(movies);

        //then
        Assert.assertFalse(result);
    }

    @Test
    void confirmExclusion_correctShortInput() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(1);
        TestUtils.fakeUserInput("y");

        //when
        boolean result = choose.confirmExclusion(movies);

        //then
        Assert.assertTrue(result);
    }

    @Test
    void confirmExclusion_wrongInput() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(2);
        TestUtils.fakeUserInput("qwerty");

        //when
        Assertions.assertThrows(NoSuchElementException.class, () -> choose.confirmExclusion(movies));
    }

    @Test
    void confirmExclusion_inputRepeated() {
        //given
        ArrayList<Movie> movies = TestCommonData.createMovieList(1);
        TestUtils.fakeUserInput("qwerty", "yes");

        //when
        boolean result = choose.confirmExclusion(movies);

        //then
        Assert.assertTrue(result);
    }
}