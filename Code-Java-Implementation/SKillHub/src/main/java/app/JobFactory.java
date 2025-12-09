package app;

// JobFactory.java
public interface JobFactory {
    Job createJob(String type, int id, String title);
}
