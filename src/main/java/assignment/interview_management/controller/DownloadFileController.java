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


/**
 * Controller xử lý chức năng tải xuống (download) file từ hệ thống.
 * Cung cấp API phục vụ việc trả về file để client hiển thị hoặc tải về.
 */
@Slf4j
@Controller
@AllArgsConstructor
public class DownloadFileController {

    private ApiDownloadService apiDownloadService;

    /**
     * API tải xuống file theo tên file được cung cấp.
     * <p>
     * File được trả về dưới dạng PDF và hiển thị trực tiếp trên trình duyệt (inline).
     *
     * @param filename tên file cần tải xuống
     * @return {@link ResponseEntity} chứa dữ liệu file dưới dạng {@link Resource}
     */
    @GetMapping(value = "/api/download")
    public ResponseEntity<Resource> download(@RequestParam("filename") String filename) {
        Resource resource = apiDownloadService.download(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=cv.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
