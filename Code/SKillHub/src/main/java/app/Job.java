package app;

// Job.java
public abstract class Job {
    protected int id;
    protected String title;
    protected String description;
    protected double budget;

    public Job(int id) { this.id = id; }

    // basic getters/setters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getBudget() { return budget; }
    public int getId() { return id; }

    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + ", title=" + title + ", budget=" + budget + "]";
    }
}
