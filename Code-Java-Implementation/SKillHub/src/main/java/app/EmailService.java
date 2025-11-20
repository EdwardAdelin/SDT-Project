package app;

// EmailService.java
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        System.out.println("[EmailService] Sending email to " + to + " : " + subject + " / " + body);
    }
}
