package com.example.LogReaderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.LogReaderService.Services.LogService;

@SpringBootTest
public class LogServiceTest {

    @Autowired
    private LogService logService;

    @Test
    void test_happyCase(@TempDir Path tempPath) throws Exception {
        File testFile = tempPath.resolve("testingFile.txt").toFile();
        
        try (FileWriter wr = new FileWriter(testFile)) {
            wr.write("testing a\n");
            wr.write("testing b\n");
            wr.write("testing c\n");
            wr.close();
        }


        List<String> result = logService.readFile(testFile.getAbsolutePath(), null, 2);
        assertEquals(2, result.size());
        assertEquals("testing c\n", result.get(0));
    }

    @Test
    void test_searchWordNotFound(@TempDir Path tempPath) throws Exception {
        File testFile = tempPath.resolve("testingFile.txt").toFile();
        
        try (FileWriter wr = new FileWriter(testFile)) {
            wr.write("testing a\n");
            wr.write("testing b\n");
            wr.write("testing c\n");
        }


        List<String> result = logService.readFile(testFile.getAbsolutePath(), "null", 2);
        assertTrue(result.isEmpty());
    }
}
