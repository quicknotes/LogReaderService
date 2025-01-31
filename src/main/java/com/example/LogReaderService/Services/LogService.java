package com.example.LogReaderService.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LogReaderService.Services.FileReaders.BottomUpReader;
import com.example.LogReaderService.Services.FileReaders.TopDownReader;

@Service
public class LogService {

    //Use topDownReader if bottomUpReader that uses RandomAccessFile isn't allowed.
    @Autowired
    TopDownReader topDownReader;

    @Autowired
    BottomUpReader bottomUpReader;

public List<String> readFile(final String filePath,
                             final String searchword,
                             final int limit) throws Exception {
        return topDownReader.readFile(filePath, searchword, limit);
    }
}
