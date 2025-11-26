package assignment.interview_management.service.impl;

import assignment.interview_management.dto.SendMailRequest;
import assignment.interview_management.exceptions.BusinessException;
import assignment.interview_management.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    private TemplateEngine templateEngine;

    public void sendEmail(SendMailRequest request, String template, Context context) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            String htmlContent = templateEngine.process(template, context);
            helper.setTo(request.getTo());
            if (request.getCc() != null && request.getCc().length > 0) {
                helper.setCc(request.getCc());
            }
            helper.setSubject(request.getSubject());
            helper.getMimeMessage().addHeader("X-Entity-Ref-ID", UUID.randomUUID().toString());
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("Gửi mail thất bại. Vui lòng thử lại sau");
        }
    }
}
