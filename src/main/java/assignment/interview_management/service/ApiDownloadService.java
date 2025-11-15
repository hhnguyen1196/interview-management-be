package assignment.interview_management.service;

import org.springframework.core.io.Resource;

public interface ApiDownloadService {

    Resource download(String filename);
}
