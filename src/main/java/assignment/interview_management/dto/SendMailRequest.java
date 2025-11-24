package assignment.interview_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMailRequest {

    private String[] to;

    private String[] cc;

    private String[] bcc;

    private String subject;
}
