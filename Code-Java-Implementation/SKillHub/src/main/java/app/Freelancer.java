package app;

// Freelancer.java
public class Freelancer extends User {
    public Freelancer(int id, String name) {
        super(id, name);
    }

    public Proposal createProposal(Job job, double rate, String desc) {
        System.out.println("[Freelancer] " + name + " creates proposal for job: " + job.getTitle());
        return new Proposal(ProposalIdGenerator.nextId(), this, job, rate, desc);
    }

    public void submitProposal(Job job, Proposal proposal, Client client) {
        System.out.println("[Freelancer] " + name + " submits proposal to client " + client.getName());
        // Simulate sending proposal to client
        client.receiveProposal(proposal);
    }

    public void receiveJobAlert(Job job) {
        System.out.println("[Freelancer] " + name + " received job alert: " + job.getTitle());
    }

    public void sendProposal(Proposal proposal, Client client) {
        System.out.println("[Freelancer] " + name + " sends proposal id " + proposal.getId() + " to client " + client.getName());
        client.receiveProposal(proposal);
    }
}
