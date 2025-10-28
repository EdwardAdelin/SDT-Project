package app;

// NotificationFacade.java
import java.util.List;

public class  NotificationFacade {
    private final EmailService emailService;
    private final SMSService smsService;

    public NotificationFacade(EmailService emailService, SMSService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    public void sendJobAlert(Job job, List<Freelancer> freelancers) {
        System.out.println("[NotificationFacade] Sending job alert for: " + job.getTitle());
        String subject = "New job posted: " + job.getTitle();
        String body = job.getDescription() + " Budget: " + job.getBudget();

        for (Freelancer f : freelancers) {
            // for PoC we use name as contact
            emailService.sendEmail(f.getName()+"@mail.test", subject, body);
            smsService.sendSMS(f.getName()+"@phone", "New job: " + job.getTitle());
        }
    }

    public void sendProposalUpdate(Proposal proposal, Client client) {
        emailService.sendEmail(client.getName()+"@mail.test", "New proposal", "Proposal id: " + proposal.getId());
        smsService.sendSMS(client.getName()+"@phone", "You have a new proposal from " + proposal.getFreelancer().getName());
    }
}
