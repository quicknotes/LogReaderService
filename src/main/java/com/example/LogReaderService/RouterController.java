package com.example.LogReaderService;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.LogReaderService.Services.LogService;


@RestController
public class RouterController {
    private static final Logger logger = LoggerFactory.getLogger(RouterController.class);

    static String FILEPATH = "/var/log/";
    @Autowired
    LogService logService;

    @GetMapping("/getlog")
    public ResponseEntity<?> getLog(@RequestParam String filename,
                                    @RequestParam(required = false) String searchword,
                                    @RequestParam(defaultValue = "100") int limit) {
        logger.debug("request received with filename {}, searchword {} limit {}",
         filename, searchword, limit);
        try {
            final String filePath = FILEPATH + filename;
            List<String> response = logService.readFile(filePath, searchword, limit);

            return ResponseEntity.ok(response + "\n");
        } catch (FileNotFoundException fileNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                String.format("File %s is not found in director %s. \n", filename, FILEPATH));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(String.format("Error reading file: %s \n", filename));
        }
    }
}
