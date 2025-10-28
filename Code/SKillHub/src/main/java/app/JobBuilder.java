package app;

// JobBuilder.java
public class  JobBuilder {
    private Job job;

    // start building from a JobFactory so we can switch job types
    public JobBuilder(JobFactory factory, String type, int id) {
        this.job = factory.createJob(type, id, "");
    }

    public JobBuilder setTitle(String title) {
        job.title = title;
        return this;
    }

    public JobBuilder setDescription(String desc) {
        job.description = desc;
        return this;
    }

    public JobBuilder setBudget(double budget) {
        job.budget = budget;
        return this;
    }

    public Job build() {
        System.out.println("[JobBuilder] Built job: " + job);
        return job;
    }
}
