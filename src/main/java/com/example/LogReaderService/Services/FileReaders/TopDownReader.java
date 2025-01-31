package com.example.LogReaderService.Services.FileReaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TopDownReader implements FileReaderInterface {

    @Override
    public List<String> readFile(final String filePath,
                                 final String searchword,
                                 final int limit) throws Exception {
        
        Path systemFilePath = Paths.get(filePath);
        
        if (!Files.exists(systemFilePath) 
            || !Files.isReadable((systemFilePath))){
                throw new FileNotFoundException();
            } 
        
        List<String> result = new ArrayList<>();
        
        //Use buffer reader to read file line by line until limit
        try (BufferedReader reader = Files.newBufferedReader(systemFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (searchword == null || line.contains(searchword)) {
                    
                    //add the line if no searchword or line contains it.
                    result.add(line + "\n");
                }
            }
        } catch (Exception e) {
            throw new FileSystemException(filePath);
        }

        Collections.reverse(result);
        if (limit > 0 && result.size() > limit) {
            result = result.subList(0, limit);
        }
        
        return result;
    }

}
