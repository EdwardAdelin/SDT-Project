package app;

// User.java
public abstract class User {
    protected int id;
    protected String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void login() {
        System.out.println("[" + getClass().getSimpleName() + "] " + name + " logged in.");
    }

    public void logout() {
        System.out.println("[" + getClass().getSimpleName() + "] " + name + " logged out.");
    }

    public String getName() { return name; }
    public int getId() { return id; }
}
