package assignment.interview_management.controller;

import assignment.interview_management.service.ApiDownloadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@AllArgsConstructor
public class DownloadFileController {

    private ApiDownloadService apiDownloadService;

    @GetMapping(value = "/api/download")
    public ResponseEntity<Resource> download(@RequestParam("filename") String filename) {
        Resource resource = apiDownloadService.download(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=cv.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
