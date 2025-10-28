package app;

// DatabaseConnection.java (simple stub)
public class DatabaseConnection {
    private final String url;
    private final String user;
    public DatabaseConnection(String url, String user) {
        this.url = url;
        this.user = user;
    }
    public void connect() {
        System.out.println("[DB] Connecting to " + url + " as " + user);
    }
}

