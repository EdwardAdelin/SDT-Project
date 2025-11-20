package app;

// ConcreteJobFactory.java
public class ConcreteJobFactory implements JobFactory {
    @Override
    public Job createJob(String type, int id, String title) {
        Job job;
        if ("fixed".equalsIgnoreCase(type)) {
            job = new FixedPriceJob(id);
        } else if ("hourly".equalsIgnoreCase(type)) {
            job = new HourlyJob(id);
        } else {
            throw new IllegalArgumentException("Unknown job type: " + type);
        }
        job.title = title;
        return job;
    }
}
