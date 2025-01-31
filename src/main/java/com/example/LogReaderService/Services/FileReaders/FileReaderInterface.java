package com.example.LogReaderService.Services.FileReaders;

import java.util.List;

public interface FileReaderInterface {
    abstract List<String> readFile(final String filePath,
                             final String searchword,
                             final int limit) throws Exception;
}
