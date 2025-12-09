package app;

// SMSService.java
public class SMSService {
    public void sendSMS(String to, String message) {
        System.out.println("[SMSService] Sending SMS to " + to + " : " + message);
    }
}
