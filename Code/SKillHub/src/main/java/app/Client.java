package app;

// Client.java
public class Client extends User {
    public Client(int id, String name) {
        super(id, name);
    }

    public void postJob(Job job, JobService jobService) {
        System.out.println("[Client] " + name + " is posting job: " + job.getTitle());
        jobService.postJob(job, this);
    }

    public void reviewProposals(Job job) {
        System.out.println("[Client] " + name + " is reviewing proposals for job: " + job.getTitle());
        // In real app: list proposals, accept one, etc.
    }

    // helper to receive proposals
    public void receiveProposal(Proposal proposal) {
        System.out.println("[Client] " + name + " received proposal from " + proposal.getFreelancer().getName()
                + " for job " + proposal.getJob().getTitle() + " with rate " + proposal.getRate());
    }
}
