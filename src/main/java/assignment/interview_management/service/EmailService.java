package assignment.interview_management.service;

import assignment.interview_management.dto.SendMailRequest;
import org.thymeleaf.context.Context;

public interface EmailService {

    void sendEmail(SendMailRequest request, String template, Context context);
}
