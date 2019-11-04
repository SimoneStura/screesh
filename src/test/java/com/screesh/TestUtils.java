package com.screesh;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class TestUtils {

    public static void fakeUserInput(String... input) {
        String inputString = String.join(" ", input);
        InputStream streamIn = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(streamIn);
    }
}
