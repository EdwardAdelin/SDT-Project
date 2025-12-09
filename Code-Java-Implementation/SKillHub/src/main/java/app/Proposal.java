package app;

// Proposal.java
public class Proposal {
    private final int id;
    private final Freelancer freelancer;
    private final Job job;
    private final double rate;
    private final String description;

    public Proposal(int id, Freelancer freelancer, Job job, double rate, String description) {
        this.id = id;
        this.freelancer = freelancer;
        this.job = job;
        this.rate = rate;
        this.description = description;
    }

    public int getId() { return id; }
    public Freelancer getFreelancer() { return freelancer; }
    public Job getJob() { return job; }
    public double getRate() { return rate; }
    public String getDescription() { return description; }
}
