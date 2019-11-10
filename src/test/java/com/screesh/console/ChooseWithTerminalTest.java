package com.screesh.console;

import com.screesh.TestCommonData;
import com.screesh.TestUtils;
import com.screesh.choosinghelper.MovieCounting;
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
        ArrayList<MovieCounting> movies = TestCommonData.createMovieCountingList(2);
        TestUtils.fakeUserInput(String.valueOf(1));

        //when
        int result = choose.chooseOneToExclude(movies);

        //then
        Assert.assertEquals(0, result);
    }

    @Test
    void chooseOneToExclude_wrongInputType() {
        //given
        ArrayList<MovieCounting> movies = TestCommonData.createMovieCountingList(2);
        TestUtils.fakeUserInput("stringValue");

        //when
        int result = choose.chooseOneToExclude(movies);

        //then
        Assert.assertEquals(-1, result);
    }

    @Test
    void chooseOneToExclude_wrongInput() {
        //given
        ArrayList<MovieCounting> movies = TestCommonData.createMovieCountingList(2);
        TestUtils.fakeUserInput(String.valueOf(3));

        //when
        int result = choose.chooseOneToExclude(movies);

        //then
        Assert.assertEquals(-1, result);
    }

    @Test
    void confirmExclusion_correctPositiveInput() {
        //given
        Movie toExclude = TestCommonData.createMovie("mov");
        TestUtils.fakeUserInput("yes");

        //when
        boolean result = choose.confirmExclusion(toExclude);

        //then
        Assert.assertTrue(result);
    }

    @Test
    void confirmExclusion_correctNegativeInput() {
        //given
        Movie toExclude = TestCommonData.createMovie("mov");
        TestUtils.fakeUserInput("no");

        //when
        boolean result = choose.confirmExclusion(toExclude);

        //then
        Assert.assertFalse(result);
    }

    @Test
    void confirmExclusion_correctShortInput() {
        //given
        Movie toExclude = TestCommonData.createMovie("mov");
        TestUtils.fakeUserInput("y");

        //when
        boolean result = choose.confirmExclusion(toExclude);

        //then
        Assert.assertTrue(result);
    }

    @Test
    void confirmExclusion_wrongInput() {
        //given
        Movie toExclude = TestCommonData.createMovie("mov");
        TestUtils.fakeUserInput("qwerty");

        //when
        Assertions.assertThrows(NoSuchElementException.class, () -> choose.confirmExclusion(toExclude));
    }

    @Test
    void confirmExclusion_inputRepeated() {
        //given
        Movie toExclude = TestCommonData.createMovie("mov");
        TestUtils.fakeUserInput("qwerty", "yes");

        //when
        boolean result = choose.confirmExclusion(toExclude);

        //then
        Assert.assertTrue(result);
    }
}