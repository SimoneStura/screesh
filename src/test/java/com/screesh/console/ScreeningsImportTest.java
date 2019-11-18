package com.screesh.console;

import com.screesh.model.FilmFestival;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningsImportTest {
    
    @Test
    void buildFromJson() throws IOException {
        //given
        String filepath = "src/test/resources/tff_37.json";
        
        //when
        FilmFestival ff = ScreeningsImport.buildFromJson(filepath);
        
        //then
        Assert.assertNotNull(ff);
    }
}