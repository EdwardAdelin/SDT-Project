package app;

// ProposalIdGenerator.java
public class ProposalIdGenerator {
    private static int id = 1;
    public static synchronized int nextId() { return id++; }
}
