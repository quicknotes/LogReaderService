package com.example.LogReaderService.Services.FileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BottomUpReader implements FileReaderInterface {
    private static final Logger logger = LoggerFactory.getLogger(BottomUpReader.class);


    @Override
    public List<String> readFile(final String filePath, final String searchword, 
                                 final int limit) throws Exception {
        
        logger.debug("readFile with filename {}, searchword {} limit {}",
        filePath, searchword, limit);

        File systemFile = new File(filePath);

        if (!systemFile.exists() 
            || !systemFile.canRead()){
            throw new FileNotFoundException();
            } 
        
        List<String> result = new ArrayList<>();
        
        //use RandomAccessFile to read in bytes from the back of the file
        try (RandomAccessFile randomAccessReader = new RandomAccessFile(systemFile, "r")) {
            Long pointer = systemFile.length() - 1;

            StringBuilder sb = new StringBuilder();
            int lineCount = 0;

            // loop until pointer is at 0 (begining of file)
            while (pointer >=0 ){
                randomAccessReader.seek(pointer);
                // just read the byte
                char character = (char) randomAccessReader.read();

                if (character == '\n') {
                    // at the end of the line, add contents from string builder to results
                    String previousLine = sb.reverse().toString();
                    if (previousLine.length() > 0
                        && (searchword == null || previousLine.contains(searchword))) {
                        logger.debug("found line with searchword {}, line:{}", searchword, previousLine);

                        result.add(previousLine + "\n");
                        lineCount++;
                        if (limit > 0 && lineCount >= limit) {
                            break;
                        }
                        // clear the string builder
                        sb.setLength(0);
                    }
                } else {
                    // not a new line add the char to string builder
                    sb.append(character);
                }
                //advance the pointer backwards
                pointer--;
            }

            //handle the first line in the file if limit is not surpassed or it not defined
            if (sb.length() > 0 && (limit > 0 || lineCount < limit)) {
                final String line = sb.reverse().toString();
                if (searchword == null || line.contains(searchword)) {
                    result.add(line);
                }
            }
        } catch (Exception e) { 
            throw new FileSystemException(systemFile.toString());
        }

        return result;
    }

}
