package app;

// JobService.java
import java.util.List;

public class JobService {
    private final NotificationFacade notificationFacade;
    private final ConfigurationManager config;

    public JobService(NotificationFacade facade) {
        this.notificationFacade = facade;
        this.config = ConfigurationManager.getInstance(); // uses singleton
    }

    public void postJob(Job job, Client client) {
        System.out.println("[JobService] Posting job: " + job.getTitle() + " by client " + client.getName());
        // simulate DB connection usage
        DatabaseConnection conn = config.getDbConnection();
        conn.connect();
        // in real app: persist job to DB
        // For the PoC we will notify some freelancers — in real app we'd query DB for matching freelancers
    }

    public void notifyFreelancers(Job job, List<Freelancer> freelancers) {
        System.out.println("[JobService] Notifying freelancers for job: " + job.getTitle());
        notificationFacade.sendJobAlert(job, freelancers);
    }
}

