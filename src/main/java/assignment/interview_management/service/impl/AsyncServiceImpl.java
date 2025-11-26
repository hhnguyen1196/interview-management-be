package assignment.interview_management.service.impl;

import assignment.interview_management.service.AsyncService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AsyncServiceImpl implements AsyncService {

    private final String uploadDir;

    public AsyncServiceImpl(@Value("${upload-dir}") String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Async
    @Override
    public void deleteFile(String filename) {
        try {
            Path folder = Paths.get(uploadDir);
            Path filePath = folder.resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
