package assignment.interview_management.service.impl;

import assignment.interview_management.service.ApiDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ApiDownloadServiceImpl implements ApiDownloadService {

    @Value("${upload-dir}")
    private String uploadDir;

    @Override
    public Resource download(String filename) {
        Path folder = Paths.get(uploadDir);
        Path filePath = folder.resolve(filename);
        try (InputStream is = new FileInputStream(filePath.toString())) {
            byte[] fileBytes = is.readAllBytes();
            return new ByteArrayResource(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
