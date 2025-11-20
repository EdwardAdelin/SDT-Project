package app;

// Main.java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Setup factories and services
        UserFactory userFactory = new ConcreteUserFactory();
        JobFactory jobFactory = new ConcreteJobFactory();

        EmailService emailService = new EmailService();
        SMSService smsService = new SMSService();
        NotificationFacade notificationFacade = new NotificationFacade(emailService, smsService);
        JobService jobService = new JobService(notificationFacade);

        // Create users (Factory Method)
        Client client = (Client) userFactory.createUser("client", 1, "AliceClient");
        Freelancer freelancer1 = (Freelancer) userFactory.createUser("freelancer", 2, "BobFreelancer");
        Freelancer freelancer2 = (Freelancer) userFactory.createUser("freelancer", 3, "CarolFreelancer");

        List<Freelancer> freelancers = new ArrayList<>();
        freelancers.add(freelancer1);
        freelancers.add(freelancer2);

        // Client builds a job using JobBuilder (Builder pattern)
        JobBuilder jobBuilder = new JobBuilder(jobFactory, "fixed", 101);
        Job job = jobBuilder
                .setTitle("Logo Design")
                .setDescription("Create logo")
                .setBudget(200.0)
                .build();

        // Sequence: Client posts job -> JobService -> NotificationFacade -> Email/SMS -> Freelancers
        client.postJob(job, jobService);
        jobService.notifyFreelancers(job, freelancers);

        // Freelancers receive the alert then create & submit proposal (follows your sequence)
        freelancer1.receiveJobAlert(job);
        Proposal p1 = freelancer1.createProposal(job, 200.0, "Delivery in 2 days");
        freelancer1.submitProposal(job, p1, client);

        // Notification to client about proposal (Facade used again)
        notificationFacade.sendProposalUpdate(p1, client);

        // Client reviews proposals
        client.reviewProposals(job);
    }
}
