package app;

// ConfigurationManager.java
public class ConfigurationManager {
    // Singleton
    private static volatile ConfigurationManager instance;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private ConfigurationManager() {
        // In real app read from file/env — here hard-coded for PoC
        this.dbUrl = "jdbc:skillhub://localhost:5432/skillhub";
        this.dbUser = "skillhub_user";
        this.dbPassword = "secret";
    }
    //singleton Dp
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) instance = new ConfigurationManager();
            }
        }
        return instance;
    }

    public DatabaseConnection getDbConnection() {
        // Return a simple stub connection
        return new DatabaseConnection(dbUrl, dbUser);
    }

    // getters
    public String getDbUrl() { return dbUrl; }
    public String getDbUser() { return dbUser; }
}
