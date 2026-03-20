package EMS.backend.service.impl;

import EMS.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @Override
    public void sendCredentialsEmail(String to, String username, String password) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Welcome to the Team! - Your Employee Account is Active");
        mailMessage.setText("Hi " + username + ",\n\n" +
                "We're excited to have you with us.\n\n" +
                "Your account has been approved by HR, and you can now log in to the Employee Management System using the credentials created by your Admin:\n\n" +
                "👤 Username: " + username + "\n" +
                "🔑 Password: " + password + "\n\n" +
                "If you have any questions, feel free to reach out to the HR department.\n\n" +
                "Best regards,\n" +
                "The HR Team");
        
        try {
            mailSender.send(mailMessage);
            logger.info("Credentials email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("CRITICAL: Failed to send credentials email to {}. Error: {}", to, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendSalaryUpdateEmail(String to, String username, double amount, String notes) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Salary Update - EMS Dashboard");
        mailMessage.setText("Hi " + username + ",\n\n" +
                "Your salary record has been updated in the Employee Management System.\n\n" +
                "👤 Username: " + username + "\n" +
                "💰 New Amount: $" + amount + "\n" +
                "📝 Notes: " + (notes != null ? notes : "N/A") + "\n\n" +
                "You can view more details by logging into your dashboard.\n\n" +
                "Best regards,\n" +
                "HR Department");
        
        try {
            mailSender.send(mailMessage);
            logger.info("Salary update email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("CRITICAL: Failed to send salary update email to {}. Error: {}", to, e.getMessage());
        }
    }
}
